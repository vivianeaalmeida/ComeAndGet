import { Injectable } from '@angular/core';
import {
  BehaviorSubject,
  catchError,
  map,
  Observable,
  of,
  throwError,
} from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { TokenService } from './token.service';
import { User1 } from '../Models/user1';
import { Route, Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private endpoint = 'http://localhost:5016/api/v1/account/';
  private endpoint2 = 'http://localhost:5016/api/v1/user/';
  private userSubject = new BehaviorSubject<User1 | null>(null);
  public user: Observable<User1 | null>;

  constructor(private http: HttpClient, private tokenSrv: TokenService, private router: Router) {
    if (this.tokenSrv.hasToken('user')) {
      this.userSubject.next(JSON.parse(this.tokenSrv.getToken('user')));
    }
    this.user = this.userSubject.asObservable();
  }

  login(email: string, password: string): Observable<any> {
    return this.http
      .post<any>(`${this.endpoint}login`, { email, password })
      .pipe(
        map((user) => {
          this.tokenSrv.saveToken('user', JSON.stringify(user));
          this.userSubject.next(user);
          return user;
        }),
        catchError(this.handleError<User1>('login'))
      );
  }

  logout() {
    this.tokenSrv.deleteToken('user');
    this.userSubject.next(null);
    this.router.navigate(['/']);
  }

  hasToken() {
    return this.tokenSrv.hasToken('user');
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      this.logout();
      console.error(error);
      console.log(`${operation} failed: ${error.message}`);
      return throwError(() => new Error(error.message));
    };
  }

    getUser(): Observable<User1> {
    console.log('Fetching user data...');
    return this.http.get<User1>(this.endpoint2).pipe(
      map((user) => {
        console.log('User data received:', user);
        return {
          username: user.username,
          email: user.email,
          name: user.name,
          phoneNumber: user.phoneNumber,
          roles: user.roles,
        };
      }),
      catchError(this.handleError<User1>('getUser'))
    );
  }
}
