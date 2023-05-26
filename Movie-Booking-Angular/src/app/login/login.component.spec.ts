import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { ServiceAppService } from '../service-app.service';

import { LoginComponent } from './login.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let service: ServiceAppService
  let router: Router
  let serviceSpy: jasmine.SpyObj<ServiceAppService>;

  beforeEach(async () => {
    const serviceSpyObj = jasmine.createSpyObj('ServiceAppService', ['Login']);

    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, FormsModule],
      declarations: [LoginComponent],
      providers: [{ provide: ServiceAppService, useValue: serviceSpyObj }
      ]

    })
      .compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(ServiceAppService);
    router = TestBed.inject(Router);
    serviceSpy = TestBed.inject(ServiceAppService) as jasmine.SpyObj<ServiceAppService>;
    fixture.detectChanges();
  });


  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should call ngonit', () => {
    component.ngOnInit();
  });


  it('should navigate to Register Page', () => {
    spyOn(router, 'navigate');
    component.OpenRegister();
    expect(router.navigate).toHaveBeenCalledWith(['/register']);
  })
  it('should alert username error', () => {
    const spy = spyOn(window, 'alert')
    component.model1.login = "";
    component.model1.password = 'password';
    component.LoginUser();
    expect(window.alert).toHaveBeenCalledWith('Username should not be empty');
  })
  it('should alert password error', () => {
    const spy = spyOn(window, 'alert')
    component.model1.login = 'username';
    component.model1.password = '';
    component.LoginUser();
    expect(window.alert).toHaveBeenCalledWith('Password should not be empty');
  })

  it('should Login and naviagte', () => {
    const response = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYWdoYXZnYXJnODM3NiIsImV4cCI6MTY4NTA1MDk2MiwiaWF0IjoxNjg1MDE0OTYyfQ._zn0vED1Z3LboqSBIfczwpHUORzQcN43NXNxw0lKj_c';
    component.model1.login = 'raghavgarg8376';
    component.model1.password = 'raghav8376';
    component.LoginUser();

    spyOn(service, "Login").and.returnValue(of(response));
    spyOn(localStorage, 'setItem');
    spyOn(service, 'setSecurityToken');
    spyOn(service, 'setLoginStatus');
    spyOn(service, 'turnOnSpecialFlag');
    const spy = spyOn(router, 'navigate')

    expect(service.Login).toHaveBeenCalledWith(component.model1);
    expect(localStorage.setItem).toHaveBeenCalledWith('IsLoggedIn', 'true');
    expect(service.getLoginStatus).toHaveBeenCalledWith();
    expect(router.navigate).toHaveBeenCalledWith(['/dashboard']);
  })



  it('should display alert on failed login', () => {
    spyOn(window, 'alert')
    serviceSpy.Login.and.returnValue(of('Invalid credentials'));
    component.model1.login = 'username';
    component.model1.password = 'password';
    component.LoginUser();
    expect(serviceSpy.Login).toHaveBeenCalledWith(component.model1);
    expect(window.alert).toHaveBeenCalledWith('Give Proper Username and Password');
  })
});
