import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ReservationAttempt } from '../Models/reservation-attempt';

@Injectable({
  providedIn: 'root',
})
export class ResAttemptService {
  endpoint = 'http://localhost:8080/api/v1/reservationAttempts';

  constructor(private http: HttpClient) {}

  getResAttempt(id: string): Observable<ReservationAttempt> {
    return this.http.get<ReservationAttempt>(`${this.endpoint}/${id}`);
  }

  addResAttempt(resAttempt: ReservationAttempt): Observable<ReservationAttempt> {
    return this.http.post<ReservationAttempt>(this.endpoint, resAttempt);
  }
}
