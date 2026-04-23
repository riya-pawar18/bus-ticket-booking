import { Component } from '@angular/core';
import {
  Router,
  RouterLink,
  RouterLinkActive,
  RouterOutlet,
} from '@angular/router';
import { AuthserviceService } from './auth/authservice.service';
import { CommonModule } from '@angular/common';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  isLoggedIn = false;
  username = '';

  constructor(
    private authService: AuthserviceService,
    private router: Router,
    private toastr: ToastrService,
  ) {}

  ngOnInit() {
    this.authService.isLoggedIn$.subscribe((status) => {
      this.isLoggedIn = status;
    });

    this.authService.username$.subscribe((name) => {
      console.log('Navbar username:', name);
      this.username = name;
    });

    this.authService.getUser().subscribe({
      next: (res: any) => {
        this.username = res.username;
        this.authService.setLoggedIn(true);
        this.authService.setUsername(res);
      },
      error: () => {
        this.authService.setLoggedIn(false);
      },
    });
  }

  logout() {
    localStorage.removeItem('token');
    this.toastr.error('Logged Out');
    this.authService.setLoggedIn(false);
    this.username = '';
    this.router.navigate(['/login']);
  }
}
