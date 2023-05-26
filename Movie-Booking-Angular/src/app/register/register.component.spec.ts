import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { RegisterComponent } from './register.component';
import { ServiceAppService } from '../service-app.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { model } from '../Models/model';
import { LoginStatus } from '../Models/LoginStatus';
import { SecurityToken } from '../Models/SecurityToken';
import { SpecialFLag } from '../Models/SpecialFlag';
import { FormsModule } from '@angular/forms';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let service: ServiceAppService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule,FormsModule],
      declarations: [RegisterComponent],
      providers: [ServiceAppService,SpecialFLag,
        SecurityToken,LoginStatus],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(ServiceAppService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should validate login ID', () => {
    const model1=new model();
    model1.login = "";
    component.chkLoginId();
    expect(component.errloginid).toBe('Login ID cannot be Empty');
    expect(component.Oklogin).toBe('');

    component.model1.login = '';
    component.chkLoginId();
    expect(component.errloginid).toBe('Login ID cannot be Empty');
    expect(component.Oklogin).toBe('');

    
  });

  it('should validate email', () => {
    const model1=new model();
    model1.email = "";
    component.chkEmail();
    expect(component.erremail).toBe('Email cannot be Empty');
    expect(component.okemail).toBe('');

    component.model1.email = '';
    component.chkEmail();
    expect(component.erremail).toBe('Email cannot be Empty');
    expect(component.okemail).toBe('');

    component.model1.email = 'invalidemail';
    component.chkEmail();
    expect(component.erremail).toBe('Please Enter a Valid Email Address');
    expect(component.okemail).toBe('');

    component.model1.email = 'email@example.com';
    component.chkEmail();
    expect(component.erremail).toBeUndefined();
    expect(component.okemail).toBe('');
  });

  it('should validate phone number', () => {
    component.model1.contactNumber = '123456';
    component.chkphn();
    expect(component.phnchk).toBe('Invalid Phone Number');

    component.model1.contactNumber = '1234567890';
    component.chkphn();
    expect(component.phnchk).toBe('');
  });

  it('should register user', () => {
    
    const model1 = {
    
      login: 'username',
      email: 'raghav@example.com',
      firstName: 'raghav',
      lastName: 'garg',
      contactNumber: '1491247890',
      password: 'password'
    };

    component.register();
    spyOn(service, 'register').and.returnValue(of(model1));
    spyOn(router, 'navigate');

    

    expect(service.register(component.model1)).toHaveBeenCalledWith({
    
      login: 'username',
      email: 'raghav@example.com',
      firstName: 'raghav',
      lastName: 'garg',
      contactNumber: '1491247890',
      password: 'password',token:'null'
    });
    expect(localStorage.getItem('loginid')).toBe('username');
    expect(localStorage.getItem('isLoggedIn')).toBe('true');
    expect(router.navigate).toHaveBeenCalledWith(['']);
  });

  it('should display error on registration failure', () => {
    spyOn(service, 'register').and.returnValue(
      of({ Empty: true }) 
    );
    spyOn(window, 'alert');

    const model1 = new model();
    
      model1.login= 'username';
      model1.email= 'raghav@example.com';
      model1.firstName= 'raghav';
      model1.lastName= 'garg';
      model1.contactNumber= '290190424890';
      model1.password= 'password';
  

    component.register();

    expect(service.register).toHaveBeenCalledWith(model1);
    expect(localStorage.getItem('loginid')).toBeNull();
    expect(localStorage.getItem('isLoggedIn')).toBeNull();
    expect(window.alert).toHaveBeenCalledWith('Fields Cannot be Empty');
  });

  it('should display error on registration', () => {
    spyOn(service, 'register').and.returnValue(throwError('Registration Error'));
    spyOn(window,'alert');

    const model1 = {
    
      login: 'username',
      email: 'raghav@example.com',
      firstName: 'raghav',
      lastName: 'garg',
      contactNumber: '1491247890',
      password: 'password'
    };
     component.register();

    expect(service.register).toHaveBeenCalledWith(component.model1);
    expect(localStorage.getItem('loginid')).toBeNull();
    expect(localStorage.getItem('isLoggedIn')).toBeNull();
    expect(window.alert).toHaveBeenCalledWith('There is some error');
  });

  it('should navigate to login page', () => {
    spyOn(router, 'navigate');

    component.OpenLogin();

    expect(router.navigate).toHaveBeenCalledWith(['']);
  });
});