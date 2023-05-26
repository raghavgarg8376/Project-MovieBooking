import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { ReactiveFormsModule, FormControl } from '@angular/forms';
import { ActivatedRoute, convertToParamMap, Router } from '@angular/router';
import { of } from 'rxjs';
import { HttpClientModule, HttpClient } from '@angular/common/http'; 

import { MovieTicketComponent } from './movie-ticket.component';
import { ServiceAppService } from '../service-app.service';
import { Ticket } from './Ticket';
import { LoginStatus } from '../Models/LoginStatus';
import { SpecialFLag } from '../Models/SpecialFlag';
import { SecurityToken } from '../Models/SecurityToken';
import { MatIconModule } from '@angular/material/icon';

describe('MovieTicketComponent', () => {
  let component: MovieTicketComponent;
  let fixture: ComponentFixture<MovieTicketComponent>;
  let service: ServiceAppService;
  let router: Router
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, ReactiveFormsModule,HttpClientModule,MatIconModule],
      declarations: [MovieTicketComponent],
      providers: [
        HttpClient,
        LoginStatus,
        ServiceAppService,
        SpecialFLag,
        SecurityToken,
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: { params: { id: '1', idm: 'theater1' }, paramMap: convertToParamMap({}) }
          }
        }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MovieTicketComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(ServiceAppService);
    router=TestBed.inject(Router);
    spyOn(service, 'getCurrentData').and.returnValue(of([
      {
        movieName: 'Movie 1',
        theaterName: [
          {
            theaterName: 'theater1',
            slots: '10:00 AM',
            seats: 100,
            bookedSeats: 50,
            releaseDate: '2023-05-26'
          },
          {
            theaterName: 'theater2',
            slots: '2:00 PM',
            seats: 80,
            bookedSeats: 20,
            releaseDate: '2023-05-27'
          }
        ]
      }
    ]));
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

 

  it('should check if user is logged in', () => {
    localStorage.setItem('IsLoggedIn', 'true');
    expect(component.LoggedIn()).toBeTrue();

    localStorage.setItem('IsLoggedIn', 'false');
    expect(component.LoggedIn()).toBeFalse();
  });

  it('should check if user is admin', () => {
    localStorage.setItem('Username', 'admin');
    expect(component.Admin()).toBeTrue();

    localStorage.setItem('Username', 'user');
    expect(component.Admin()).toBeFalse();
  });

  it('should log out the user', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    spyOn(router, 'navigate');

    component.Logout();

    expect(localStorage.getItem('IsLoggedIn')).toBe('false');
    expect(localStorage.getItem('login')).toBeNull();
    expect(localStorage.getItem('Token')).toBeNull();
    localStorage.clear();
    expect(localStorage.length).toBe(0);
    expect(router.navigate).toHaveBeenCalledWith(['']);
  });

  it('should submit the form and book the movie ticket', () => {
    

    // const dummyTicket: Ticket = {
    //   movieName:'Movie 1',
    //   theaterName:'Theater 1',
    //   timeSlot:'4-7',
    //   noOfSeats:2,
    //   bookingForDate:'27-02-2000'
    // };
    spyOn(window, 'alert');
    spyOn(router, 'navigate');
    spyOn(service, 'bookTicket').and.returnValue(of(true));


    component.tickets.bookingForDate='23-08-2000';
    component.submitForm();

    
    expect(service.bookTicket).toHaveBeenCalledWith(component.tickets);
    expect(window.alert).toHaveBeenCalledWith('Movie ticket is booked');
    expect(router.navigate).toHaveBeenCalledWith(['/dashboard']);
  });

  it('should update the form and movie details', () => {
    component.tickets.bookingForDate = '2023-05-26';
    component.UpdateForm();

    spyOn(window, 'alert');
    spyOn(router, 'navigate');
    //spyOn(service, 'UpdateMovie').and.returnValue(of(true));

    

    expect(service.UpdateMovie).toHaveBeenCalledWith(component.tickets);
    //expect(window.alert).toHaveBeenCalledWith('Movie Details is Updated');
    expect(router.navigate).toHaveBeenCalledWith(['/dashboard']);
  });
});
