import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthserviceService {
  private baseUrl = 'http://localhost:8080/auth';

  private loggedIn = new BehaviorSubject<boolean>(false);
  isLoggedIn$ = this.loggedIn.asObservable();

  private username = new BehaviorSubject<string>('');
  username$ = this.username.asObservable();

  setUsername(name: string) {
    this.username.next(name);
  }

  constructor(private http: HttpClient) {}

  login(user: any) {
    return this.http.post(`${this.baseUrl}/login`, user, {
      withCredentials: true,
    });
  }

  register(user: any) {
    return this.http.post(`${this.baseUrl}/register`, user);
  }

  logout() {
    this.setLoggedIn(false);
  }

  setLoggedIn(value: boolean) {
    this.loggedIn.next(value);
  }

  // 🔥 single method only
  getUser() {
    return this.http.get(`${this.baseUrl}/me`, {
      withCredentials: true,
    });
  }
}
