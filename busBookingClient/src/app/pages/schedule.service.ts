import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ScheduleService {
  private baseUrl = 'http://localhost:8080/bus';

  constructor(private http: HttpClient) {}

  getSchedules(source: string, dest: string) {
    return this.http.get(`${this.baseUrl}/schedules`, {
      params: {
        source: source,
        dest: dest,
      },
    });
  }
}
