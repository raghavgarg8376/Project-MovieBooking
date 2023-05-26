import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { MovieBookingComponent } from './movie-booking.component';
import { ServiceAppService } from '../service-app.service';
import { of, throwError } from 'rxjs';
import { Allmovie } from '../dashboard/Allmovie';
import { Theatre } from '../dashboard/Theatre';
import { FormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { SpecialFLag } from '../Models/SpecialFlag';
import { SecurityToken } from '../Models/SecurityToken';
import { LoginStatus } from '../Models/LoginStatus';
import { MatIconModule } from '@angular/material/icon';

describe('MovieBookingComponent', () => {
  let component: MovieBookingComponent;
  let fixture: ComponentFixture<MovieBookingComponent>;
  let service: ServiceAppService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule,FormsModule,MatIconModule],
      declarations: [MovieBookingComponent],
      providers: [
        SpecialFLag,
        SecurityToken,LoginStatus,
        { provide: ServiceAppService, useValue: jasmine.createSpyObj('ServiceAppService', ['AddMovie']) },
        { provide: Router, useValue: jasmine.createSpyObj('Router', ['navigate']) }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MovieBookingComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(ServiceAppService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should add theater to the list', () => {
    component.addTheater();
    expect(component.theaters.length).toBe(1);
    expect(component.theaters[0]).toEqual({ theaterName: '', seats: 0, slots: '', bookedSeats: 0, releaseDate: '' });
  });

  it('should remove theater from the list', () => {
    component.theaters = [
      { theaterName: 'Theater 1', seats: 100, slots: 'Morning', bookedSeats: 0, releaseDate: '2023-05-26' },
      { theaterName: 'Theater 2', seats: 200, slots: 'Evening', bookedSeats: 0, releaseDate: '2023-05-27' }
    ];

    component.removeTheater();

    expect(component.theaters.length).toBe(1);
    expect(component.theaters[0]).toEqual({ theaterName: 'Theater 1', seats: 100, slots: 'Morning', bookedSeats: 0, releaseDate: '2023-05-26' });
  });

  it('should return true when user is logged in', () => {
    spyOn(localStorage, 'getItem').and.returnValue('true');

    const result = component.LoggedIn();

    expect(result).toBe(true);
    expect(localStorage.getItem).toHaveBeenCalledWith('IsLoggedIn');
  });

  it('should return false when user is not logged in', () => {
    spyOn(localStorage, 'getItem').and.returnValue('false');

    const result = component.LoggedIn();

    expect(result).toBe(false);
    expect(localStorage.getItem).toHaveBeenCalledWith('IsLoggedIn');
  });


  it('should logout user and navigate to login page', () => {
    

    component.Logout();

    spyOn(localStorage, 'getItem').and.returnValue('true');
    spyOn(localStorage, 'setItem');
    spyOn(localStorage, 'removeItem');
    spyOn(localStorage, 'clear');
    spyOn(window, 'alert');
    spyOn(router, 'navigate');

    expect(localStorage.setItem).toHaveBeenCalledWith('IsLoggedIn', 'false');
    expect(localStorage.removeItem).toHaveBeenCalledWith('login');
    expect(localStorage.removeItem).toHaveBeenCalledWith('Token');
    expect(localStorage.clear).toHaveBeenCalled();
    expect(window.alert).toHaveBeenCalledWith('You have been Logged Out');
    expect(router.navigate).toHaveBeenCalledWith(['']);
  });

  
  it('should submit movie details form and navigate to dashboard on success', () => {
    const movie: Allmovie = {
      movieName: 'Movie 1',
      theaterName: [
        { theaterName: 'Theater 1', seats: 100, slots: 'Morning', bookedSeats: 0, releaseDate: '2023-05-26' },
        { theaterName: 'Theater 2', seats: 200, slots: 'Evening', bookedSeats: 0, releaseDate: '2023-05-27' }
      ]
    };

    
    component.movies = movie;
    component.submitForm();
    spyOn(service, 'AddMovie').and.returnValue(of());
    spyOn(window, 'alert');
    spyOn(router, 'navigate');


    expect(service.AddMovie).toHaveBeenCalledWith(movie);
    expect(window.alert).toHaveBeenCalledWith('Movie Details is booked');
    expect(router.navigate).toHaveBeenCalledWith(['/dashboard']);
  });

  it('should display error message on submission failure', () => {
    spyOn(service, 'AddMovie').and.returnValue(throwError('Submission Error'));
    spyOn(window, 'alert');

    component.submitForm();

    expect(service.AddMovie).toHaveBeenCalledWith(component.movies);
    expect(window.alert).toHaveBeenCalledWith('Some Problem Occured, Please try again');
  });
});
