import { Component,inject,OnInit, signal } from '@angular/core';
import { Tasks } from '../services/tasks';

@Component({
  selector: 'app-my-task-component',
  imports: [],
  templateUrl: './my-task-component.html',
  styleUrl: './my-task-component.css',
})
export class MyTaskComponent implements OnInit{
  private taskService = inject(Tasks);
  tasks= signal<any[]>([]);
  ngOnInit(): void {
    
      this.taskService.getTasks().subscribe(res => {
        console.log('API Response:', res);
        this.tasks.set(res);
      })
     
  
  }



}
