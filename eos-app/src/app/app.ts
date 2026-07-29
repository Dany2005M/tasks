import { Component, inject, signal } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { Users } from './services/users';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('eos-app');

  private userService = inject(Users);
  username = this.userService.currentUser;

  isMenuCollapsed = true;

  isAdmin(): boolean {
    const token = localStorage.getItem('TASKS_TOKEN');
    if(!token) return false;

    try{
      const payloadBase64 = token.split('.')[1];
      const decodedPayload = JSON.parse(atob(payloadBase64));

      return  decodedPayload.role === 'ADMIN';
    } catch(error){
      console.error('Error from decoding token:', error);
      return false;
    }
  }
}
