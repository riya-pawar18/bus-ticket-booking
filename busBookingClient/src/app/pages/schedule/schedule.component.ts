import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ScheduleService } from '../schedule.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-schedule',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './schedule.component.html',
  styleUrl: './schedule.component.css',
})
export class ScheduleComponent {
  constructor(
    private scheduleService: ScheduleService,
    private router: Router,
  ) {}

  schedule = {
    source: '',
    destination: '',
  };

  schedules: any[] = [];

  states: string[] = [
    // States
    'Andhra Pradesh',
    'Arunachal Pradesh',
    'Assam',
    'Bihar',
    'Delhi',
    'Chhattisgarh',
    'Goa',
    'Gujarat',
    'Haryana',
    'Himachal Pradesh',
    'Jharkhand',
    'Karnataka',
    'Kerala',
    'Madhya Pradesh',
    'Maharashtra',
    'Manipur',
    'Meghalaya',
    'Mizoram',
    'Nagaland',
    'Odisha',
    'Punjab',
    'Rajasthan',
    'Sikkim',
    'Tamil Nadu',
    'Telangana',
    'Tripura',
    'Uttar Pradesh',
    'Uttarakhand',
    'West Bengal',
  ];

  searchSchedule() {
    this.scheduleService
      .getSchedules(this.schedule.source, this.schedule.destination)
      .subscribe({
        next: (res: any) => {
          this.schedules = res;
          console.log(res);
        },
        error: (err) => console.log(err),
      });
  }
  goToBooking(id: number) {
    console.log(id);
    this.router.navigate(['/booking', id]);
  }
}
