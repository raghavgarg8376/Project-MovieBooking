import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ServiceAppService } from '../service-app.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {

  constructor(
    private service:ServiceAppService, 
    private router:Router
    ) { }
  email:String="";
  fetch:String="";
  submitForm(){
    console.log(this.email)
    this.service.forgotPassword(this.email).subscribe(
      (data:any)=>{
          console.log(data)
          
      }
    )
  }
}
