import { Component, inject } from '@angular/core';
import { Users } from '../services/users';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Login } from '../services/login';
import { LoginDTO } from '../interfaces/loginDTO';
import LocalStorageUtils from '../utils/localStorageUtils';
import { RegisterDTO } from '../interfaces/registerDTO';

@Component({
  selector: 'app-login-component',
  imports: [FormsModule],
  templateUrl: './login-component.html',
  styleUrl: './login-component.css',
})
export class LoginComponent {
  

  isLoginMode: boolean = true;
  userToLogin: LoginDTO = {} as LoginDTO;
  userToRegister: RegisterDTO ={
    email: '',
    password: '',
    username: '',
    birthDate: ''
  };
  private userService = inject(Users);
  private router = inject(Router);
  private loginService = inject(Login);

  login() : void {
      const encodedeUserDTO: LoginDTO = {
          email: btoa(this.userToLogin.email || ''),
          password: btoa(this.userToLogin.password || '')
      }

      this.loginService.postLogin(encodedeUserDTO).subscribe({
          next: (response) => {
              console.log('Login successful:', response);

              if(response.startsWith('403')){
                console.error('Login failed: Invalid credentials');
                return;
              }

              LocalStorageUtils.setItem(LocalStorageUtils.tokenKey, response);
              
              const tokenPayloadBase64 = response.split('.')[1];
              const decodePayload = JSON.parse(atob(tokenPayloadBase64));

              LocalStorageUtils.setItem('username', decodePayload.username);

              this.userService.setLoggedInUser(decodePayload.username);

              this.router.navigate(['/home']);
            
                

            },
            error: error => {
                console.error('Login failed:', error);
            }
      });
    }

    register(): void{
      const encodedRegisterDTO: RegisterDTO = {
      email: btoa(this.userToRegister.email),
      password: btoa(this.userToRegister.password),
      username: this.userToRegister.username,
      birthDate: this.userToRegister.birthDate
      };

      this.loginService.postRegister(encodedRegisterDTO).subscribe({
        next: (response) => {
          console.log('Registration successful!', response);
          alert('Account created successfully! You can now log in.');
          this.isLoginMode = true;
        },
        error: (error) => {
        if (error.status === 409) {
          console.error('Registration failed: Email already exists');
          alert('This email is already in use.');
        } else {
          console.error('An unexpected error occurred:', error);
        }
      }
      })
    }

  }

