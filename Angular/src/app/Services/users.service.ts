import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { User1 } from '../Models/user1';

@Injectable({
  providedIn: 'root',
})
export class UsersService {
  theEndpoint = 'http://localhost:5016/api/v1/account/register';
  private userUrl = 'http://localhost:5016/api/v1/user/';
  constructor(private http: HttpClient) {}

  registerUser(newUser: User1): Observable<any> {
    return this.http.post<any>(this.theEndpoint, newUser);
  }

    getUserById(userId:string): Observable<User1> {
      return this.http.get<User1>(`${this.userUrl}/${userId}`);
    }

  

}
