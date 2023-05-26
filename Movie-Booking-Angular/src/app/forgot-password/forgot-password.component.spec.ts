import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { ForgotPasswordComponent } from './forgot-password.component';
import { ServiceAppService } from '../service-app.service';
import { of, throwError } from 'rxjs';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';

describe('ForgotPasswordComponent', () => {
  let component: ForgotPasswordComponent;
  let fixture: ComponentFixture<ForgotPasswordComponent>;
  let service: ServiceAppService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule,FormsModule,MatIconModule],
      declarations: [ForgotPasswordComponent],
      providers: [
        { provide: ServiceAppService, useValue: jasmine.createSpyObj('ServiceAppService', ['forgotPassword']) },
        { provide: Router, useValue: jasmine.createSpyObj('Router', ['navigate']) }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ForgotPasswordComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(ServiceAppService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should call forgotPassword service method and navigate to password page on successful submission', () => {
    const email = 'test@example.com';
    component.submitForm();
    spyOn(service, 'forgotPassword').and.returnValue(of('true'));
    spyOn(router, 'navigate');


    expect(service.forgotPassword).toHaveBeenCalledWith(email);
    expect(router.navigate).toHaveBeenCalledWith(['']);
  });

  it('should handle error and not navigate on submission failure', () => {
    const email = 'test@example.com';
    component.email = email;
    component.submitForm();

    spyOn(service, 'forgotPassword').and.returnValue(throwError('Error'));
    spyOn(router, 'navigate');

    

    expect(service.forgotPassword).toHaveBeenCalledWith(email);
    expect(router.navigate).not.toHaveBeenCalled();
  });
});