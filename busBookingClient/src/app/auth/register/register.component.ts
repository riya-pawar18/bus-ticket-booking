import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthserviceService } from '../authservice.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [RouterLink, FormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent {
  user = {
    name: '',
    username: '',
    password: '',
    phone: '',
  };

  msg: string = '';
  success: boolean = false;

  constructor(
    private authService: AuthserviceService,
    private router: Router,
    private toastr: ToastrService,
  ) {}

  onRegister() {
    this.authService.register(this.user).subscribe({
      next: (res: any) => {
        this.msg = res.message || 'Registered successfully';
        this.success = true;
        this.toastr.success('Registration Successful');

        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 1500);
      },
      error: (err) => {
        console.log(err);
        this.msg = err.error?.message || 'Registration failed';
        this.toastr.error('Registration Failed', this.msg);
        this.success = false;
      },
    });
  }
}
