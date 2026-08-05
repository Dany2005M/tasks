import { Component,inject,OnInit, signal } from '@angular/core';
import { Tasks } from '../services/tasks';
import { NewTaskComponent } from '../new-task-component/new-task-component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Statuses } from '../services/statuses';
import { TaskDTO } from '../interfaces/TaskDTO';
import { StatusDTO } from '../interfaces/StatusDTO';
import LocalStorageUtils from '../utils/localStorageUtils';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-my-task-component',
  imports: [FormsModule],
  templateUrl: './my-task-component.html',
  styleUrl: './my-task-component.css',
})
export class MyTaskComponent implements OnInit{
  private taskService = inject(Tasks);
  private modalService = inject(NgbModal);
  private statusService = inject(Statuses);

  tasks = signal<TaskDTO[]>([]);

  availableStatuses = signal<StatusDTO[]>([]);

  isAdmin: boolean = false;

  currentPage: number = 0;
  pageSize: number = 10;
  sortBy: string = 'taskId';
  sortDirection: string = 'ASC';
  totalPages: number = 0;

  ngOnInit(): void {
    this.loadTasks();
    this.statusService.getStatuses().subscribe((res: StatusDTO[]) => this.availableStatuses.set(res));
    this.isAdmin = this.getRole() === 'ADMIN';
  }

  getStatusName(id: string | null): string {
    const status = this.availableStatuses().find(s => s.statusTypeId === id);
    return status ? status.statusName : 'Loading...';
  }

  loadTasks() : void {
    this.taskService.getTasks(this.currentPage, this.pageSize, this.sortBy, this.sortDirection).subscribe(res => {
      console.log('API Response:', res);
      
      this.tasks.set(res.content);
      
      this.totalPages = res.totalPages;
    });
  }

  nextPage() : void {
    if(this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadTasks();
    }
  }

  previousPage() : void {
    if(this.currentPage > 0) {
      this.currentPage--;
      this.loadTasks();
    }
  }

  changeSort(field: string) : void {
    if(this.sortBy === field) {
      this.sortDirection = this.sortDirection === 'ASC' ? 'DESC' : 'ASC';
    } else {
      this.sortBy = field;
      this.sortDirection = 'ASC';
    }

    this.currentPage = 0;
    this.loadTasks();
  }

  openTaskModal(task: TaskDTO | null = null) : void {
    const modalRef = this.modalService.open(NewTaskComponent, { size: 'lg' });
    modalRef.componentInstance.taskToEdit = task;
  

  modalRef.result.then((result) => {
  
    if(result) {
      console.log('DEBUG: The result object is:', result);
      if(result.taskId) {
        this.taskService.updateTask(result).subscribe(() => {
          this.loadTasks();
        });
      } else {
        this.taskService.createTask(result).subscribe(() => {
          this.loadTasks();
        });
      }
    }}).catch((error) => {
      console.log('Modal dismissed');
    });
  }

  deleteTask(taskId: number | undefined) : void {
    if(!taskId) return;

    if(confirm('Are you sure you want to delete this task?')) {
      this.taskService.deleteTask(taskId).subscribe(() => {
        this.loadTasks();
      });
    }
  }

  getRole(): string | null {
    const token = LocalStorageUtils.getItem("TASKS_TOKEN"); 

    if (!token) {
        return null;
    }

    try {
   
        const tokenPayloadBase64 = token.split('.')[1];
        const decodePayload = JSON.parse(atob(tokenPayloadBase64));
        
        return decodePayload.role; 
    } catch (error) {
        console.error("Eroare la decodarea token-ului:", error);
        return null;
    }

    
}
  getId(): number | null {
      const token = LocalStorageUtils.getItem("TASKS_TOKEN"); 

    if (!token) {
        return null;
    }

    try {
   
        const tokenPayloadBase64 = token.split('.')[1];
        const decodePayload = JSON.parse(atob(tokenPayloadBase64));
        
        return decodePayload.userId; 
    } catch (error) {
        console.error("Eroare la decodarea token-ului:", error);
        return null;
    }
    }

    onStatusChange(taskId: number | undefined, newStatusId: string): void {
      if(!taskId || !newStatusId) return;

      this.taskService.updateTaskStatus(taskId, newStatusId).subscribe({
        next: () => {
          this.loadTasks();
        },
        error: (err) => {
          alert('Error updating task status: ' + err.message);

          this.loadTasks();
        }
      });
    }
}
