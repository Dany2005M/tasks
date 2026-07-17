import { Component, inject } from '@angular/core';
import { Users } from '../services/users';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login-component',
  imports: [FormsModule],
  templateUrl: './login-component.html',
  styleUrl: './login-component.css',
})
export class LoginComponent {
  isLoginMode: boolean = true;
  private userService = inject(Users);
  private router = inject(Router);

  authData: any = {
    email: '',
    password: ''
  };

  registerData: any = {
    username: '',
    birthDate: '',
    email: '',
    password: '',
    isInternal: false,
    createdByFullName: 'SUMMER_SCHOOL'
  };

  setMode(mode: 'login' | 'register'): void {
    this.isLoginMode = (mode === 'login');
  }

  onAuthenticate(): void {
      this.userService.loginUser(this.authData).subscribe({
        next: (response) => {
          console.log('Login successful:', response);
          this.userService.setLoggedInUser(response.username);
          this.router.navigate(['/home']);
        },
        error: (error) => {
          console.error('Login failed:', error);
          alert('Login failed. Please check your credentials and try again.');
        }
      });
  }

  onRegister(): void {
    this.userService.createUser(this.registerData).subscribe({
      next: (response) => {
        this.authData.email = this.registerData.email;
        this.authData.password = this.registerData.password;
        
        this.onAuthenticate();
      },
      error: (error) => {
        console.error('Registration failed:', error);
        alert('Registration failed. Please check your details and try again.');
      }
    });
  }
}
