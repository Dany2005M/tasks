import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import LocalStorageUtils from '../utils/localStorageUtils';
import { Users } from '../services/users';

@Component({
  selector: 'app-home-component',
  imports: [],
  templateUrl: './home-component.html',
  styleUrl: './home-component.css',
})
export class HomeComponent {
  private router = inject(Router);
  private userService = inject(Users);
  logout(): void {
    LocalStorageUtils.clear();

    this.userService.setLoggedInUser(null);

    this.router.navigate(['/login']);
  }
}
