import { Component,inject,OnInit, signal } from '@angular/core';
import { Tasks } from '../services/tasks';
import { NewTaskComponent } from '../new-task-component/new-task-component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Statuses } from '../services/statuses';
import { TaskDTO } from '../interfaces/TaskDTO';
import { StatusDTO } from '../interfaces/StatusDTO';

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

  tasks = signal<TaskDTO[]>([]);

  availableStatuses = signal<StatusDTO[]>([]);
  ngOnInit(): void {
    this.loadTasks();
    this.statusService.getStatuses().subscribe((res: StatusDTO[]) => this.availableStatuses.set(res));
  }

  getStatusName(id: string | null): string {
    const status = this.availableStatuses().find(s => s.statusTypeId === id);
    return status ? status.statusName : 'Loading...';
  }

  loadTasks() : void {
    this.taskService.getTasks().subscribe(res => {
      console.log('API Response:', res);
      const sortedTasks = res.sort((a: TaskDTO, b: TaskDTO) => {
        const dateA = new Date(a.dueDate).getTime();
        const dateB = new Date(b.dueDate).getTime();
        
        return dateB - dateA; 
      });
      this.tasks.set(sortedTasks);
    });
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
}
