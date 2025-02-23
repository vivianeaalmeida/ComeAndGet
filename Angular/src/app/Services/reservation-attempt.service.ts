import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ReservationAttempt } from '../Models/reservation-attempt';
import { ReservationAttemptResponse } from '../Models/reservation-attempt-response';

@Injectable({
  providedIn: 'root'
})
export class ReservationAttemptService {

  endpoint = 'http://localhost:8080/api/v1/reservationAttempts';
  
  constructor(private http: HttpClient) {}

  getReservationAttemptById(id:string):Observable<ReservationAttempt>{
    return this.http.get<ReservationAttempt>(`${this.endpoint}/${id}`)
  }

  getUserRequestAttempts(loggedUserId:string):Observable<ReservationAttemptResponse[]>{
    return this.http.get<ReservationAttemptResponse[]>(`${this.endpoint}?reservationAttemptClientId=${loggedUserId}`)
  }

  createReservationAttempt(reservation:ReservationAttempt):Observable<ReservationAttempt>{
    return this.http.post<ReservationAttempt>(this.endpoint, reservation)
  }

  updateReservationAttemptStatus(id:string, status:string):Observable<ReservationAttempt>{
    return this.http.post<ReservationAttempt>(`${this.endpoint}/${id}/status`, status)
  }

}