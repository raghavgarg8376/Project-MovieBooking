import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { model } from '../Models/model';
import { ServiceAppService } from '../service-app.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {


  model1: model = new model()
  errloginid!: string
  erremail!: string
  phnchk!: string
  Oklogin!: string
  okemail!: string
  regex = new RegExp('[A-Za-z0-9]+@[a-z]+\.[a-z]{2,3}');

  constructor(private service: ServiceAppService, private router: Router) { }

  ngOnInit(): void {

  }

  chkLoginId() {
    if (this.model1.login == null || this.model1.login.length == 0) {
      this.errloginid = "Login ID cannot be Empty"
      this.Oklogin = "";
    }
  }

  chkEmail() {

    if (this.model1.email == null || this.model1.email.length == 0) {
      this.erremail = "Email cannot be Empty"
      this.okemail = ""
    }
    else if (!this.regex.test(this.model1.email)) {
      this.erremail = "Please Enter a Valid Email Address"
      this.okemail = "";
    }

  }

  chkphn() {
    if (this.model1.contactNumber.length != 10 || isNaN(Number(this.model1.contactNumber)))
      this.phnchk = "Invalid Phone Number"
    else
      this.phnchk = "";
  }

  register() {
    if (this.model1.email == null || this.model1.login == null || this.model1.firstName == null ||
      this.model1.lastName == null || this.model1.contactNumber == null || this.model1.password == null ||
      this.model1.email.length == 0 || this.model1.login.length == 0 || this.model1.firstName.length == 0 ||
      this.model1.lastName.length == 0 || this.model1.contactNumber.length == 0 || this.model1.password.length == 0) {
      alert("Fields Cannot be Empty")
    }

    else {
      this.service.register(this.model1).subscribe(data => {
        localStorage.setItem('loginid', this.model1.login)
        localStorage.setItem('isLoggedIn', "true")
        this.router.navigate([''])
        alert('user is successfully registered');
      },
        error => {
          alert("There is Some Error")
        }
      )
    }
  }

  OpenLogin() {
    this.router.navigate([''])
  }
}
