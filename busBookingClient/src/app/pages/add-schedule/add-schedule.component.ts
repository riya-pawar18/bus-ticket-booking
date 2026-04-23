import { Component } from '@angular/core';
import { DashboardService } from '../dashboard.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-add-schedule',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './add-schedule.component.html',
})
export class AddScheduleComponent {
  schedule = {
    routeId: '',
    departureTime: '',
    scheduleDt: '',
    avlSeats: '',
    totSeats: '',
    busFare: '',
  };

  constructor(
    private service: DashboardService,
    private router: Router,
    private toastr: ToastrService,
  ) {}

  addSchedule() {
    const data = {
      ...this.schedule,
      departureTime: this.schedule.departureTime + ':00',
    };
    this.service.addSchedule(data).subscribe({
      next: () => {
        this.toastr.success('Schedule added successfully');
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        console.log(err);
        this.toastr.error('Failed to Add Schedule', err.error.message);
      },
    });
  }
}
