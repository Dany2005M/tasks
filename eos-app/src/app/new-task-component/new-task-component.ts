import { Component, inject, Input, OnInit, signal } from '@angular/core';
import { Tasks } from '../services/tasks';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { Statuses } from '../services/statuses';
import { TaskDTO } from '../interfaces/TaskDTO';
import { StatusDTO } from '../interfaces/StatusDTO';
import LocalStorageUtils from '../utils/localStorageUtils';
import { Users } from '../services/users';
import { UserDTO } from '../interfaces/UserDTO';
@Component({
  selector: 'app-new-task-component',
  imports: [FormsModule],
  templateUrl: './new-task-component.html',
  styleUrl: './new-task-component.css',
})
export class NewTaskComponent implements OnInit {
  @Input() taskToEdit: TaskDTO | null = null;
  activeModal = inject(NgbActiveModal);
  isEditMode = false;

  private taskService = inject(Tasks);
  private statusService = inject(Statuses);
  private userService = inject(Users);

  availableStatuses = signal<StatusDTO[]>([]);
  availableUsers = signal<UserDTO[]>([]);
  formData: TaskDTO = {
    name: '',
    dueDate: '',
    userId:null,
    createdByFullName: 'SUMMER_SCHOOL',
    statusTypeId: null
  };

  ngOnInit(): void {
    this.statusService.getStatuses().subscribe((statuses) => {
      this.availableStatuses.set(statuses);
  
    if (this.taskToEdit) {
      this.isEditMode = true;
      this.formData = { ...this.taskToEdit };
      
    }
    else if(this.availableStatuses().length > 0) {
        this.formData.statusTypeId = this.availableStatuses()[0]?.statusTypeId || null;
      }
  });
    this.userService.getUsers().subscribe((users) => {
      this.availableUsers.set(users);
    })
  }

    saveTask(): void {
    console.log('Sending this to backend:', this.formData); 
    this.activeModal.close(this.formData);
  }


}




