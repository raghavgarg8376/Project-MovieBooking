import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { RegisterComponent } from './register/register.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './login/login.component';
import { DatePipe } from '@angular/common';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { SecurityToken } from './Models/SecurityToken';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SpecialFLag } from './Models/SpecialFlag';
import { LoginStatus } from './Models/LoginStatus';
import { MovieTicketComponent } from './movie-ticket/movie-ticket.component';
import { MovieBookingComponent } from './movie-booking/movie-booking.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    ForgotPasswordComponent,
    DashboardComponent,
    MovieTicketComponent,
    MovieBookingComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatToolbarModule,
    MatIconModule,
    BrowserAnimationsModule
  ],
  providers: [DatePipe, HttpClient,SecurityToken,SpecialFLag,LoginStatus],
  bootstrap: [AppComponent]
})
export class AppModule { }
