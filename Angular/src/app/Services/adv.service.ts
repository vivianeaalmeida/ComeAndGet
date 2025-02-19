import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AdvService {
  endpoint = 'http://localhost:8080/api/v1/';

  constructor(private myWeb: HttpClient) {}

  add(value?: string, any?: any): Observable<any> {
    return this.myWeb.post<any>(this.endpoint + value, any);
  }
}
