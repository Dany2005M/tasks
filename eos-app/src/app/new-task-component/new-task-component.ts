import { Component, inject, Input, OnInit, signal } from '@angular/core';
import { Tasks } from '../services/tasks';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { Statuses } from '../services/statuses';
@Component({
  selector: 'app-new-task-component',
  imports: [FormsModule],
  templateUrl: './new-task-component.html',
  styleUrl: './new-task-component.css',
})
export class NewTaskComponent implements OnInit {
  @Input() taskToEdit: any;
  activeModal = inject(NgbActiveModal);
  isEditMode = false;

  private taskService = inject(Tasks);
  private statusService = inject(Statuses);

  availableStatuses = signal<any[]>([]);
  formData: any = {
    name: '',
    dueDate: '',
    userId:Number(sessionStorage.getItem('userId')) || null,
    createdByFullName: 'SUMMER_SCHOOL',
    statusType: null
  };

  ngOnInit(): void {
    this.statusService.getStatuses().subscribe((statuses) => {
      this.availableStatuses.set(statuses);
    

    if (this.taskToEdit) {
      this.isEditMode = true;
      this.formData = { ...this.taskToEdit };

      if(this.taskToEdit.statusType) {
        this.formData.statusType = this.availableStatuses().find(status => status.statusTypeId === this.taskToEdit.statusType.statusTypeId);
      }
      
    }
    else if(this.availableStatuses().length > 0) {
        this.formData.statusType = this.availableStatuses()[0];
      }
  });
  }

    saveTask(): void {
    const payload = { ...this.formData };
console.log('Sending this to backend:', payload); 
    if (payload.statusType && payload.statusType.statusTypeId) {
      payload.statusTypeId = payload.statusType.statusTypeId;
    }
    
    delete payload.statusType;

    this.activeModal.close(payload);
  }


}




