import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { DashboardComponent } from './dashboard.component';
import { ServiceAppService } from '../service-app.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { Allmovie } from './Allmovie';
import { Router } from '@angular/router';
import { SecurityToken } from '../Models/SecurityToken';
import { SpecialFLag } from '../Models/SpecialFlag';
import { LoginStatus } from '../Models/LoginStatus';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;
  let service: ServiceAppService;
  let router: Router
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule, MatIconModule, FormsModule],
      declarations: [DashboardComponent],
      providers: [ServiceAppService, SpecialFLag,
        SecurityToken, LoginStatus],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(ServiceAppService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch all movies on initialization', () => {
    const dummyMovies: Allmovie[] = [
      { movieName: 'Movie 1', theaterName: [] },
      { movieName: 'Movie 2', theaterName: [] },
    ];

    spyOn(service, 'moviesall').and.returnValue(of(dummyMovies));

    component.ngOnInit();

    expect(service.moviesall).toHaveBeenCalled();
    expect(component.fetch).toEqual(dummyMovies);
  });

  it('should check if user is logged in', () => {
    localStorage.setItem('IsLoggedIn', 'true');
    expect(component.LoggedIn()).toBe(true);

    localStorage.setItem('IsLoggedIn', 'false');
    expect(component.LoggedIn()).toBe(false);

    localStorage.removeItem('IsLoggedIn');
    expect(component.LoggedIn()).toBe(false);
  });

  it('should check if user is admin', () => {
    localStorage.setItem('Username', 'admin');
    expect(component.Admin()).toBe(true);

    localStorage.setItem('Username', 'user');
    expect(component.Admin()).toBe(false);

    localStorage.removeItem('Username');
    expect(component.Admin()).toBe(false);
  });

  it('should logout user', () => {
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


  it('should search for movies', () => {
    const dummySearchQuery = 'movie';
    const dummyMovies: Allmovie[] = [
      { movieName: 'Movie 1', theaterName: [] },
      { movieName: 'Movie 2', theaterName: [] },
    ];

    spyOn(service, 'searchmoviesall').and.returnValue(of(dummyMovies));

    component.searchQuery = dummySearchQuery;
    component.searchMovies();

    expect(service.searchmoviesall).toHaveBeenCalledWith(dummySearchQuery);
    expect(component.fetch).toEqual(dummyMovies);
  });

  it('should delete movies', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    spyOn(service, 'DeleteMovies').and.returnValue(of('success'));

    const dummyMovieName = 'Movie 1';

    component.deleteMovies(dummyMovieName);

    expect(window.confirm).toHaveBeenCalled();
    expect(service.DeleteMovies).toHaveBeenCalledWith('Movie 1');
    expect(window.alert).toHaveBeenCalledWith('success');
    expect(component.ngOnInit).toHaveBeenCalled();
  });
});
