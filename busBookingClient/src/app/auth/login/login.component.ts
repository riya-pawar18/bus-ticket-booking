import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AuthserviceService } from '../authservice.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterLink, FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  user = {
    username: '',
    password: '',
  };
  msg: string = '';
  success: boolean = false;

  constructor(
    private authService: AuthserviceService,
    private router: Router,
    private toastr: ToastrService,
  ) {}
  onLogin() {
    this.authService.login(this.user).subscribe({
      next: (res: any) => {
        localStorage.setItem('token', res.token);

        this.authService.setLoggedIn(true);

        this.authService.getUser().subscribe((res: any) => {
          this.authService.setUsername(res.username);
        });
        this.toastr.success('Login Successful');
        this.router.navigate(['/schedule']);
      },
      error: () => {
        this.msg = 'Login failed';
        this.toastr.error('Login Failed');
      },
    });
  }
}
