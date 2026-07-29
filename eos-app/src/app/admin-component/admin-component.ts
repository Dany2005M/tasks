import { Component, inject, OnInit, signal } from '@angular/core';
import { UserDTO } from '../interfaces/UserDTO';
import { Users } from '../services/users';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-component',
  imports: [FormsModule],
  templateUrl: './admin-component.html',
  styleUrl: './admin-component.css',
})
export class AdminComponent implements OnInit{
  users = signal<UserDTO[]>([]);
  
  private userService = inject(Users);
  ROLES = [
    {id: 1, name: 'ADMIN'},
    {id: 2, name: 'USER'}
  ];

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.userService.getUsers().subscribe({
      next: (response) => this.users.set(response),
      error: (error) => console.error('Error loading users:', error)
    });
  }

  onRoleChange(userId:number | undefined, newRoleId: number): void {
    if(!userId) return;

    const isSure = confirm("Are you sure you want to change this user's role?");

    if(isSure){
      this.userService.changeRole(userId, newRoleId).subscribe({
        next: () => {
          alert('The role was modified successfully!')
          this.loadUsers();
        },
        error: (error) => {
          console.error('Error:', error);
          alert('Changing user role failed.')
          this.loadUsers();
        }
      });
    }
    else {
      this.loadUsers();
    }
  }

  getRoleName(roleId: number): string {
    const role = this.ROLES.find(r => r.id === roleId);
    return role ? role.name : 'FARA_ROL';
  }


}
