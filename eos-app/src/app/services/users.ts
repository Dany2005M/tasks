import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Users {
  private http = inject(HttpClient);
  
private userSignal = signal<string | null>(localStorage.getItem('username'));

  currentUser = this.userSignal.asReadonly();
  public createUser(user: any){
    return this.http.post('http://localhost:8080/users', user);
  }

  public getUsers(): Observable<any[]> {
    return this.http.get<any[]>('http://localhost:8080/users');
  }

  public loginUser(credentials: any): Observable<any> {
    return this.http.post<any>('http://localhost:8080/users/login', credentials);
  }

  public setLoggedInUser(username: string | null){
    this.userSignal.set(username);
  }
}
