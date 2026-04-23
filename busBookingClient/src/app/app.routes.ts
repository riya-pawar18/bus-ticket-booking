import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './auth/login/login.component';
import { registerLocaleData } from '@angular/common';
import { RegisterComponent } from './auth/register/register.component';
import { ScheduleComponent } from './pages/schedule/schedule.component';
import { BookingComponent } from './pages/booking/booking.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { AddScheduleComponent } from './pages/add-schedule/add-schedule.component';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  {
    path: 'home',
    component: HomeComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  { path: 'register', component: RegisterComponent },
  { path: 'schedule', component: ScheduleComponent },
  { path: 'booking', component: BookingComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'booking/:id', component: BookingComponent },
  { path: 'add-schedule', component: AddScheduleComponent },
];
