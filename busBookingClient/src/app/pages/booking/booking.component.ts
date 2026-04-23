import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BookingService } from '../booking.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-booking',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './booking.component.html',
  styleUrl: './booking.component.css',
})
export class BookingComponent {
  scheduleId!: number;

  seats: any[] = [];
  totalSeats = 40;

  selectedSeats: number[] = [];
  bookedSeats: number[] = [];

  passengers: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private bookingService: BookingService,
    private toastr: ToastrService,
  ) {}

  ngOnInit() {
    this.scheduleId = Number(this.route.snapshot.paramMap.get('id'));

    this.generateSeats();

    this.loadBookedSeats();
  }

  loadBookedSeats() {
    this.bookingService
      .getBookedSeats(this.scheduleId)
      .subscribe((res: any) => {
        this.bookedSeats = res;
        console.log('Booked seats:', this.bookedSeats);
      });
  }

  generateSeats() {
    this.seats = [];

    for (let i = 1; i <= this.totalSeats; i += 4) {
      this.seats.push({
        left: [i, i + 1],
        right: [i + 2, i + 3],
      });
    }
  }

  toggleSeat(seat: number) {
    if (this.bookedSeats.includes(seat)) return;

    if (this.selectedSeats.includes(seat)) {
      this.selectedSeats = this.selectedSeats.filter((s) => s !== seat);
      this.passengers = this.passengers.filter((p) => p.seatNo !== seat);
    } else {
      this.selectedSeats.push(seat);
      this.passengers.push({
        passengerName: '',
        passengerAge: '',
        seatNo: seat,
      });
    }
  }

  isSelected(seat: number) {
    return this.selectedSeats.includes(seat);
  }

  isBooked(seat: number) {
    return this.bookedSeats.includes(seat);
  }

  book() {
    const data = {
      scheduleId: this.scheduleId,
      passengers: this.passengers,
    };

    this.bookingService.bookTicket(data).subscribe({
      next: () => {
        this.toastr.success('Booking successful ✅');

        // 🔥 refresh booked seats
        this.loadBookedSeats();

        // 🔥 clear selection
        this.selectedSeats = [];
        this.passengers = [];
      },
      error: (err) => {
        this.toastr.error('Booking failed ❌');
        console.log(err);
      },
    });
  }
}
