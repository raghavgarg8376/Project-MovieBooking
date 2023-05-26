import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { model } from '../Models/model';
import { ServiceAppService } from '../service-app.service';
import { ReactiveFormsModule, FormControl, FormGroup } from '@angular/forms';
import { Allmovie } from '../dashboard/Allmovie';
import { ActivatedRoute } from '@angular/router';
import { Ticket } from './Ticket';

@Component({
  selector: 'app-movie-ticket',
  templateUrl: './movie-ticket.component.html',
  styleUrls: ['./movie-ticket.component.css']
})
export class MovieTicketComponent implements OnInit {


  regex = new RegExp('[A-Za-z0-9]+@[a-z]+\.[a-z]{2,3}');
  form: any;
  fetch: Allmovie[] = [];
  data: any;
  tickets: Ticket = new Ticket();
  element: number = 0;
  userName:String="";
  editdata = new FormGroup({
    movieName: new FormControl(''),
    theaterName: new FormControl(''),
    timeSlot: new FormControl(''),
    noOfSeats: new FormControl(),
    bookingForDate: new FormControl(),
  })

  constructor(private service: ServiceAppService, private router: Router, private rout: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.userName=localStorage.getItem('Username') || "";
    console.log(this.rout.snapshot.params['id'])
    this.service.getCurrentData(this.rout.snapshot.params['id']).subscribe((result) => {
      console.log("raghav")

      console.log(result);
      for (let index = 0; index < result[0].theaterName.length; index++) {
        if (this.rout.snapshot.params['idm'] == result[0].theaterName[index].theaterName) {
          this.element = index
        }

      }
      console.log(this.element)
      // this.editdata=new FormGroup({
      //   movieName:new FormControl(result[0].movieName),
      //   theaterName:new FormControl(result[0].movieName),
      //   timeSlot:new FormControl(result[0].movieName),
      //   noOfSeats:new FormControl(result[0].movieName)
      // })
      // for (let index = 0; index < result[0].theaterName.length; index++) {
      //   if(this.rout.snapshot.params['idm']==result[index].theaterName[0].theaterName){
      //     this.element=index
      //   }

      // }
      // console.log(this.element)
      this.editdata.patchValue({
        movieName: (result[0].movieName),

        theaterName: (result[0].theaterName[this.element].theaterName),
        timeSlot: (result[0].theaterName[this.element].slots),
        noOfSeats: (result[0].theaterName[this.element].seats - result[0].theaterName[this.element].bookedSeats),
        bookingForDate: (result[0].theaterName[this.element].releaseDate)
      })

      console.log(this.editdata)
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
  
  Logout(){
    
      localStorage.setItem('IsLoggedIn', "false")
      localStorage.removeItem('login')
      localStorage.removeItem('Token')
      localStorage.clear
      alert("You have been Logged Out")
      this.router.navigate([''])
    
  }


  submitForm() {
    console.log(this.tickets.bookingForDate)
    this.service.bookTicket(this.tickets).subscribe(
      (data) => {
        console.log(data)
        if (data) {
          window.alert("Movie ticket is booked")
          this.router.navigate(['/dashboard'])
        }
        

      },
      error => {
        window.alert("Give Proper Username and Password")
      }
    );
  }
  UpdateForm() {
    console.log(this.tickets.bookingForDate)
    this.service.UpdateMovie(this.tickets).subscribe(
      data => {
        console.log(data)
        if (data) {
          window.alert("Movie Details is Updated")
          this.router.navigate(['/dashboard'])
        }

      },
      error => {
        window.alert("Give Proper Username and Password")
      }
    );
  }
}
