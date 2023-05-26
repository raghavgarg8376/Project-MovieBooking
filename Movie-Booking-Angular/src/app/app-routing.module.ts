import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
// import { ChangePasswordComponent } from './change-password/change-password.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { LoginComponent } from './login/login.component';
import { MovieBookingComponent } from './movie-booking/movie-booking.component';
import { MovieTicketComponent } from './movie-ticket/movie-ticket.component';
import { RegisterComponent } from './register/register.component';

const routes: Routes = [
  {
    path:'register',
    component:RegisterComponent
  },
  {
    path:'',
    component:LoginComponent
  },
  {
    path:'forgotpassword',
    component:ForgotPasswordComponent
  },
  {
    path:'dashboard',
    component:DashboardComponent
  },
  {
    path:'movie/:id/:idm',
    component:MovieTicketComponent
  },
  // {
  //   path:'password',
  //   component:ChangePasswordComponent
  // },
  {
    path:'addmovie',
    component:MovieBookingComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
