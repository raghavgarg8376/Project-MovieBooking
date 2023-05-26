import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Allmovie } from '../dashboard/Allmovie';
import { Theatre } from '../dashboard/Theatre';
import { ServiceAppService } from '../service-app.service';

@Component({
  selector: 'app-movie-booking',
  templateUrl: './movie-booking.component.html',
  styleUrls: ['./movie-booking.component.css']
})
export class MovieBookingComponent implements OnInit {

  constructor(private service:ServiceAppService, private router:Router) { }

  userName:string="";
  movies: Allmovie={
    movieName:"",
    theaterName:[]
  };
  theaters: Theatre[]=[];
  ngOnInit(): void {
    this.userName=localStorage.getItem('Username') || "";
  }
  addTheater(){
    this.theaters.push({theaterName:'',seats:0,slots:'',bookedSeats:0,releaseDate:''})
  }
  removeTheater(){
    this.theaters.pop()
  }
  LoggedIn(){
    return localStorage.getItem('IsLoggedIn')=='true';
  }
  Logout(){
    
      localStorage.setItem('IsLoggedIn', "false")
      localStorage.removeItem('login')
      localStorage.removeItem('Token')
      localStorage.clear
      alert("You have been Logged Out")
      this.router.navigate([''])
  }
  submitForm(){
  
    
    this.movies.theaterName=this.theaters
    this.service.AddMovie(this.movies).subscribe(
      data => {
        
          window.alert("Movie Details is booked")
          this.router.navigate(['/dashboard'])
        

      },
      error => {
        window.alert("Some Problem Occured, Please try again")
      }
    );
  }
}
