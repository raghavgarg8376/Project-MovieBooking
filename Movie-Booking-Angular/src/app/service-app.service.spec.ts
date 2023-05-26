import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ServiceAppService } from './service-app.service';
import { Allmovie } from './dashboard/Allmovie';
import { Ticket } from './movie-ticket/Ticket';
import { LoginStatus } from './Models/LoginStatus';
import { SecurityToken } from './Models/SecurityToken';
import { SpecialFLag } from './Models/SpecialFlag';
import { model } from './Models/model';

describe('ServiceAppService', () => {
  let service: ServiceAppService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ServiceAppService,SpecialFLag,
        SecurityToken,LoginStatus],
    });
    service = TestBed.inject(ServiceAppService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  it('should turn on the special flag', () => {
    service.turnOnSpecialFlag();
    expect(service.getSpecialFlag()).toBe(true);
  });
  it('should turn off the special flag', () => {
    service.turnOnSpecialFlag();
    service.turnOffSpecialFlag();
    expect(service.getSpecialFlag()).toBe(false);
  });


  it('should get the login status', () => {
    service.setLoginStatus(true);
    expect(service.getLoginStatus()).toBe(true);
  });

  it('should get the Security Token', () => {
    service.setSecurityToken("true");
    expect(service.getSecurityToken()).toBe("true");
  });
 


 
  it('should search the api', () => {
    const mockData="abcd";
    service.searchmoviesall(mockData).subscribe();
    const req=httpMock.expectOne('http://localhost:8082/movies/search/abcd');
    expect(req.request.method).toBe('GET');

  });

  it('should search the api', () => {
    const mockData="abcd";
    service.getCurrentData(mockData).subscribe();
    const req=httpMock.expectOne('http://localhost:8082/movies/search/abcd');
    expect(req.request.method).toBe('GET');

  });

  it('should get the token', () => {
    const mockData="abcd";
    service.forgotPassword(mockData).subscribe();
    const req=httpMock.expectOne('http://localhost:8082/abcd/forgot');
    expect(req.request.method).toBe('GET');

  });

  it('should register the model successfully', () => {
    const model:model={login:'raghavgarg8376',password:'password',email:'raghavgarg8376@gmail.com',firstName:'raghav',lastName:'garg',contactNumber:'8391798835',token:'token'};
    service.register(model).subscribe();
    const req=httpMock.expectOne('http://localhost:8082/register');
    expect(req.request.method).toBe('POST');

  });
  it('should login successfully', () => {
    const model1={login:'raghavgarg8376',password:'password'};
    service.Login(model1).subscribe();
    const req=httpMock.expectOne('http://localhost:8082/login');
    expect(req.request.method).toBe('POST');

  });



  it('should get all movies', () => {
    const dummyMovies: Allmovie[] = [
      // Provide sample data here
    ];

    service.moviesall().subscribe((movies) => {
      expect(movies).toEqual(dummyMovies);
    });

    const req = httpMock.expectOne('http://localhost:8082/all');
    expect(req.request.method).toBe('GET');
    req.flush(dummyMovies);
  });

  it('should search movies by title', () => {
    const searchQuery = 'search query';
    const dummyMovies: Allmovie[] = [
      {movieName:'Movie 1',theaterName:[]},
      {movieName:'Movie 2',theaterName:[]}
    ];

    service.searchmoviesall(searchQuery).subscribe((movies) => {
      expect(movies).toEqual(dummyMovies);
    });

    const req = httpMock.expectOne(`http://localhost:8082/movies/search/${searchQuery}`);
    expect(req.request.method).toBe('GET');
    req.flush(dummyMovies);
  });

  it('should book a movie ticket', () => {
    const dummyTicket: Ticket = {
      movieName:'Movie 1',
      theaterName:'Theater 1',
      timeSlot:'4-7',
      noOfSeats:2,
      bookingForDate:'27-02-2000'
    };

    service.bookTicket(dummyTicket).subscribe((response) => {
      expect(response).toBeTruthy();
    });

    const req = httpMock.expectOne('http://localhost:8082/add');
    expect(req.request.method).toBe('POST');
    expect(req.request.headers.get('content-type')).toBe('application/json');
    expect(req.request.headers.get('Authorization')).toBe('Bearer ' + localStorage.getItem('Authorization'));
    req.flush(dummyTicket);
  });

  it('should update movie details', () => {
    const dummyTicket: Ticket = {
      movieName:'Movie 1',
      theaterName:'Theater 1',
      timeSlot:'4-7',
      noOfSeats:2,
      bookingForDate:'27-02-2000'

    };

    service.UpdateMovie(dummyTicket).subscribe((response) => {
      expect(response).toBeTruthy();
    });

    const req = httpMock.expectOne('http://localhost:8082/update');
    expect(req.request.method).toBe('PUT');
    expect(req.request.headers.get('content-type')).toBe('application/json');
    expect(req.request.headers.get('Authorization')).toBe('Bearer ' + localStorage.getItem('Authorization'));
    req.flush(dummyTicket);
  });

  it('should add a movie', () => {
    const dummyMovie: Allmovie = {
      movieName:'Movie 1',
      theaterName:[]
    };

    service.AddMovie(dummyMovie).subscribe((response) => {
      expect(response).toBeTruthy();
    });

    const req = httpMock.expectOne('http://localhost:8082/addmovie');
    expect(req.request.method).toBe('POST');
    expect(req.request.headers.get('content-type')).toBe('application/json');
    expect(req.request.headers.get('Authorization')).toBe('Bearer ' + localStorage.getItem('Authorization'));
    req.flush(dummyMovie);
  });

  it('should delete a movie', () => {
    const movieId = 'movie-id';

    service.DeleteMovies(movieId).subscribe((response) => {
      expect(response).toBeTruthy();
    });

    const req = httpMock.expectOne(`http://localhost:8082/${movieId}/delete`);
    expect(req.request.method).toBe('DELETE');
    expect(req.request.headers.get('Authorization')).toBe('Bearer ' + localStorage.getItem('Authorization'));
    req.flush({});
  });
});
