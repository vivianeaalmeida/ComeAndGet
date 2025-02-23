import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Tip } from '../Models/tip';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TipService {

  endpoint = 'http://localhost:5016/api/v1/Tips';

  constructor(private http: HttpClient) {}

  getTips():Observable<Tip[]> {
    return this.http.get<Tip[]>(this.endpoint)
  }

  getTipById(id:number):Observable<Tip> {
    return this.http.get<Tip>(`${this.endpoint}/${id}`)
  }

  addTip(tip:Tip):Observable<Tip> {
    return this.http.post<Tip>(this.endpoint, tip)
  }

  updateTip(id:number, tip:Tip):Observable<Tip> {
    return this.http.put<Tip>(`${this.endpoint}/${id}`, tip)
  }

  deleteTip(id:number):Observable<Tip> {
    return this.http.delete<Tip>(`${this.endpoint}/${id}`)
  }
}
