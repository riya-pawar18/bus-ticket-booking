import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  private baseUrl = 'http://localhost:8080/bus';

  constructor(private http: HttpClient) {}

  getMyBookings() {
    return this.http.get(`${this.baseUrl}/bookings/customer`);
  }

  addSchedule(data: any) {
    return this.http.post('http://localhost:8080/bus/schedule', data);
  }
}
