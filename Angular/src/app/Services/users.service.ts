import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { User1 } from '../Models/user1';

@Injectable({
  providedIn: 'root',
})
export class UsersService {
  theEndpoint = 'http://localhost:5016/api/v1/account/register';
  constructor(private myWebApiClient: HttpClient) {}

  registerUser(newUser: User1): Observable<any> {
    return this.myWebApiClient.post<any>(this.theEndpoint, newUser);
  }
}
