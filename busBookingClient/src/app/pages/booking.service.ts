import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class BookingService {
  private baseUrl = 'http://localhost:8080/bus';

  constructor(private http: HttpClient) {}

  getBookedSeats(id: number) {
    return this.http.get(`${this.baseUrl}/booked/${id}`);
  }

  bookTicket(data: any) {
    return this.http.post(`${this.baseUrl}/book-ticket`, data);
  }
}
