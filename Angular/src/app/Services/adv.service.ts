import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Advertisement } from '../Models/advertisement';

@Injectable({
  providedIn: 'root',
})
export class AdvService {
  endpoint = 'http://localhost:8080/api/v1/';

  constructor(private myWeb: HttpClient) {}

  get(value?: string): Observable<any[]> {
    return this.myWeb.get<any[]>(this.endpoint + value);
  }

  getById(value?: string): Observable<Advertisement> {
    return this.myWeb.get<Advertisement>(
      this.endpoint + 'advertisements/' + value
    );
  }

  add(value?: string, any?: any): Observable<any> {
    return this.myWeb.post<any>(this.endpoint + value, any);
  }

  updateAdvertisement(
    id: string,
    advertisement: Advertisement
  ): Observable<Advertisement> {
    return this.myWeb.put<Advertisement>(
      this.endpoint + 'advertisements/' + id,
      advertisement
    );
  }
}
