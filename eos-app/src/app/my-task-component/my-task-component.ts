import { Component,inject,OnInit, signal } from '@angular/core';
import { Tasks } from '../services/tasks';
import { NewTaskComponent } from '../new-task-component/new-task-component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Statuses } from '../services/statuses';

@Component({
  selector: 'app-my-task-component',
  imports: [],
  templateUrl: './my-task-component.html',
  styleUrl: './my-task-component.css',
})
export class MyTaskComponent implements OnInit{
  private taskService = inject(Tasks);
  private modalService = inject(NgbModal);
  private statusService = inject(Statuses);

  tasks = signal<any[]>([]);

  availableStatuses = signal<any[]>([]);
  ngOnInit(): void {
    this.loadTasks();
    this.statusService.getStatuses().subscribe(res => this.availableStatuses.set(res));
  }

  getStatusName(id: string): string {
    const status = this.availableStatuses().find(s => s.statusTypeId === id);
    return status ? status.statusName : 'Loading...';
  }

  loadTasks() : void {
    this.taskService.getTasks().subscribe(res => {
      console.log('API Response:', res);
      const sortedTasks = res.sort((a: any, b: any) => {
        const dateA = new Date(a.dueDate).getTime();
        const dateB = new Date(b.dueDate).getTime();
        
        return dateB - dateA; 
      });
      this.tasks.set(sortedTasks);
    });
  }

  openTaskModal(task: any = null) : void {
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

  deleteTask(taskId: number) : void {
    if(confirm('Are you sure you want to delete this task?')) {
      this.taskService.deleteTask(taskId).subscribe(() => {
        this.loadTasks();
      });
    }
  }
}
