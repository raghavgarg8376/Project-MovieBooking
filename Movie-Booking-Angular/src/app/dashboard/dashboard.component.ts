import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { model } from '../Models/model';
import { ServiceAppService } from '../service-app.service';
import { Allmovie } from './Allmovie';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {


  fetch: Allmovie[] = [];
  IsLoggedIn: boolean = false;
  searchQuery: String = "";
  public isLoaded: boolean = false;
  userName: string = "";
  model1: model = new model()

  constructor(
    private service: ServiceAppService,
    private router: Router
  ) { }


  selectedMovie: any = null;


  ngOnInit(): void {

    // this.service.allmovie()
    // .then( ( res: Allmovie[] ) => {
    //   console.log(res);
    // }).finally( ()=> {
    //   this.isLoaded = true;
    // } )
    console.log(localStorage)
    this.userName = localStorage.getItem('Username') || "";

    console.log(localStorage.getItem('IsLoggedIn'))
    this.service.moviesall().subscribe(
      data => {
        this.fetch = data.sort((a, b) => a.movieName.localeCompare(b.movieName));
        if (data.length == 0) {

          this.router.navigate([""]);
        }
      },
      error => {
        alert("Give Proper Username and Password")
      }
    )

  }

  LoggedIn() {
    if (localStorage.getItem('IsLoggedIn') == 'true') {
      return true
    }
    return false
  }
  Admin() {
    if (localStorage.getItem('Username') == 'admin') {
      return true
    }
    return false
  }
  Logout() {

    localStorage.setItem('IsLoggedIn', "false")
    localStorage.removeItem('login')
    localStorage.removeItem('Token')
    localStorage.clear
    alert("You have been Logged Out")
    this.router.navigate([''])

  }
  searchMovies() {
    console.log("yes")

    const api = '${this.searchQuery}'
    if (api == null) {
      this.ngOnInit();
      return
    }
    this.service.searchmoviesall(this.searchQuery).subscribe(
      data => {
        this.fetch = data
      }
    )
  }
  deleteMovies(movieName: string) {
    var retVal = confirm("Do you want to continue ?");
    if (retVal == true) {
      this.service.DeleteMovies(movieName).subscribe(
        data => {
          window.alert(data)
          this.ngOnInit();
        },

      )
    }



  }
}
