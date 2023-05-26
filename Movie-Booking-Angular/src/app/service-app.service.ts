import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tick } from '@angular/core/testing';
import { Observable } from 'rxjs';
import { Allmovie } from './dashboard/Allmovie';
import { LoginStatus } from './Models/LoginStatus';
import { model } from './Models/model';
import { SecurityToken } from './Models/SecurityToken';
import { SpecialFLag } from './Models/SpecialFlag';
import { Ticket } from './movie-ticket/Ticket';

@Injectable({
  providedIn: 'root'
})
export class ServiceAppService {

  private usertoken = 'Bearer ' + localStorage.getItem('Token');
  private _responses: Allmovie[] = [];
  private baseUrl: string = "http://localhost:8082"
  private registerUrl = "http://localhost:8082/register"
  constructor(
    private http: HttpClient,
    private token: SecurityToken,
    private loginStatus: LoginStatus,
    private specialFlag: SpecialFLag
  ) { }

  login: string | null = localStorage.getItem('login')
  password!: string
  model!: model

  turnOnSpecialFlag() {
    this.specialFlag.flag = true;
  }
  turnOffSpecialFlag() {
    this.specialFlag.flag = false;
  }

  getSpecialFlag(): boolean {
    return this.specialFlag.flag;
  }

  Login(data: any) {
    return this.http.post(`${this.baseUrl}/login`, data, {
      responseType: 'text',
      //observe : 'response'
    })
  }

  register(model: model): Observable<any> {
    // console.log(model);
    return this.http.post(`${this.registerUrl}`, model);
  }


  moviesall(): Observable<Allmovie[]> {
    return this.http.get<Allmovie[]>('http://localhost:8082/all')
  }

  searchmoviesall(search: String): Observable<Allmovie[]> {
    console.log(search)
    return this.http.get<Allmovie[]>('http://localhost:8082/movies/search/' + search)
  }

  setLoginStatus(status: boolean) {
    this.loginStatus.Status = status;
  }

  getLoginStatus() {
    return this.loginStatus.Status;
  }
  getSecurityToken() {
    // take result from AuthorizationMSClient service, using the security-token :: subscribe
    return this.token.Jwt;

  }
  setSecurityToken(token: string) {
    // take result from AuthorizationMSClient service, using the security-token :: subscribe
    this.token.Jwt = token;
  }
  forgotPassword(email: any) {
    return this.http.get('http://localhost:8082/' + email + '/forgot')
  }

  getCurrentData(id: any): Observable<Allmovie[]> {
    return this.http.get<Allmovie[]>('http://localhost:8082/movies/search/' + id)
  }

  bookTicket(ticket: Ticket): Observable<any> {
    const headers = new HttpHeaders()
      .set('content-type', 'application/json')
      .set("Authorization", "Bearer " + localStorage.getItem('Authorization'));

    return this.http.post<any>('http://localhost:8082/add', ticket, { headers: headers })
  }

  UpdateMovie(ticket: Ticket): Observable<Ticket[]> {
    const headers = new HttpHeaders()
      .set('content-type', 'application/json')
      .set("Authorization", "Bearer " + localStorage.getItem('Authorization'));


    return this.http.put<Ticket[]>('http://localhost:8082/update', ticket, { headers: headers })
  }

  AddMovie(ticket: Allmovie): Observable<Allmovie[]> {
    
    const headers = new HttpHeaders()
      .set('content-type', 'application/json')
      .set("Authorization", "Bearer " + localStorage.getItem('Authorization'));


    return this.http.post<Allmovie[]>('http://localhost:8082/addmovie', ticket, { headers: headers })
  }

  DeleteMovies(moviedetail:any): Observable<any> {
    const headers = new HttpHeaders()
      .set("Authorization", "Bearer " + localStorage.getItem('Authorization'));

    // console.log(localStorage)
    return this.http.delete<any>('http://localhost:8082/'+moviedetail+'/delete', { headers: headers, responseType:'text' as 'json' });
    
  }
}
