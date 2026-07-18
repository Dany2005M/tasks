import { Component, inject, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Tasks } from '../services/tasks';
import { Statuses } from '../services/statuses';
import { Users } from '../services/users';

@Component({
  selector: 'app-search-component',
  imports: [FormsModule],
  templateUrl: './search-component.html',
  styleUrl: './search-component.css',
})
export class SearchComponent implements OnInit {
  
  private taskService = inject(Tasks);
  private statusService = inject(Statuses);
  private userService = inject(Users);

  statusDictionary: { [key: string]: string } = {};
  userDictionary: { [key: number]: string } = {};
  availableStatuses = signal<any[]>([]);
  
  searchParams: any = {
    subject: '',
    assignedTo: '',
    dueDate: '',
    status: ''
  };

  searchResults = signal<any[]>([]);
  hasSearched: boolean = false;

ngOnInit(): void {
   this.statusService.getStatuses().subscribe((statuses) => {
      this.availableStatuses.set(statuses);

      statuses.forEach((status: any) => {
        this.statusDictionary[status.statusTypeId] = status.statusName;
      });
    this.userService.getUsers().subscribe((users) => {
      users.forEach((user: any) => {
        this.userDictionary[user.userId] = user.username;
      });
    });
  });
}

  getStatusName(statusId: string): string {
    return this.statusDictionary[statusId] || 'Loading...';
  }

  getUsername(userId: number): string {
    return this.userDictionary[userId] || 'Loading...';
  }

  onSearch(): void {
    this.hasSearched = true;

    const cleanParams = Object.fromEntries(
      Object.entries(this.searchParams).filter(([_, value]) => value !== '' && value !== null)
    );

    this.taskService.searchTasks(cleanParams).subscribe({
      next: (results) => {
        this.searchResults.set(results);
        console.log('Search results:', results);
      },
      error: (error) => {
        console.error('Search failed:', error);
        alert('Search failed. Please try again.');
      }
    });

    
  }

  clearFilters(): void {
      this.searchParams = {
        subject: '',
        assignedTo: '',
        dueDate: '',
        status: ''
      };
      this.searchResults.set([]);
      this.hasSearched = false;
    }

}
