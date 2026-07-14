import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class Statuses {
  private http = inject(HttpClient);

  public getStatuses() {
    return this.http.get<any[]>('http://localhost:8080/statuses');
  }
}
