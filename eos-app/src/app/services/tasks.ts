import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class Tasks {
  private http = inject(HttpClient);

  public getTasks(page: number = 0, size: number = 10, sortBy: string = 'taskId', sortDirection: string = 'ASC') {
    return this.http.get<any>('http://localhost:8080/tasks', {params: {page, size, sortBy, sortDirection}});
  }

  public searchTasks(searchCriteria: any, page: number = 0, size: number = 10, sortBy: string = 'taskId', sortDirection: string = 'ASC') {
    return this.http.get<any>('http://localhost:8080/tasks/search', {params: {...searchCriteria, page, size, sortBy, sortDirection}});
  }

  public createTask(task: any) {
    return this.http.post('http://localhost:8080/tasks', task);
  }

  public updateTask(task: any) {
    return this.http.put(`http://localhost:8080/tasks/${task.taskId}`, task);
  }

  public updateTaskStatus(taskId: number, newStatusId: string) {
    return this.http.patch(`http://localhost:8080/tasks/${taskId}/status/${newStatusId}`, {});
  }

  public deleteTask(taskId: number) {
    return this.http.delete(`http://localhost:8080/tasks/${taskId}`);
  }
  


}
