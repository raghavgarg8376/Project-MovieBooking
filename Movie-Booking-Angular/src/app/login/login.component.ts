import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { model } from '../Models/model';
import { ServiceAppService } from '../service-app.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  model1 :model=new model()
  constructor(private service:ServiceAppService, private router:Router) { }


  ngOnInit(): void {
  }


  OpenRegister(){
    
    this.router.navigate(['/register'])
  }

  LoginUser(){
    this.service.login=this.model1.login;
    this.service.password=this.model1.password;
    this.service.model=this.model1;

    if(this.model1.login==""){
      alert("Username should not be empty")
    }
    else if(this.model1.password==""){
      alert("Password should not be empty")
    }else{
    this.service.Login(this.model1).subscribe(
      data=>
     {
      if(data.includes(".")){
      //console.log(data)
      localStorage.setItem('IsLoggedIn',"true")
      localStorage.setItem('Authorization',data)
      localStorage.setItem('Username',this.model1.login)
      this.service.setSecurityToken(data);
      this.service.setLoginStatus(true);
      this.service.turnOnSpecialFlag();
      this.router.navigate(['/dashboard'])
      }else{
        alert("Give Proper Username and Password")
      }
     },
     error=>
     {
       alert("Give Proper Username and Password")
     }
   );
 }
}
}
