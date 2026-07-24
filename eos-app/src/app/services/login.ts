import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { LoginDTO } from '../interfaces/loginDTO';
import { RegisterDTO } from '../interfaces/registerDTO';

@Injectable({
  providedIn: 'root',
})
export class Login {

  private http = inject(HttpClient);
  public postLogin(user: LoginDTO) {
      return this.http.post<String>('http://localhost:8080/tasks/login', user,
        {
          responseType: 'text' as 'json',
        }
      );
  }

  public postRegister(user: RegisterDTO) {
      return this.http.post('http://localhost:8080/tasks/register', user, 
        {
          responseType: 'text' as 'json',
        }
      );
  }


}
