import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class Tasks {
  private http = inject(HttpClient);

  public getTasks() {
    return this.http.get<any[]>('http://localhost:8080/tasks');
  }

  public createTask(task: any) {
    return this.http.post('http://localhost:8080/tasks', task);
  }

  public updateTask(task: any) {
    return this.http.put(`http://localhost:8080/tasks/${task.taskId}`, task);
  }

  public deleteTask(taskId: number) {
    return this.http.delete(`http://localhost:8080/tasks/${taskId}`);
  }
  


}
