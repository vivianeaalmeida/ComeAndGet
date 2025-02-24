import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Interaction } from '../Models/interaction';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class InteractionsService {
  endpoint = 'http://localhost:5016/api/v1/interactions';

  constructor(private http: HttpClient) {}

  getInteractionsByUser(userId?: string): Observable<Interaction[]> {
    return this.http.get<Interaction[]>(`${this.endpoint}/users/${userId}`);
  }

  addNewInteraction(interaction: any): Observable<Interaction> {
    return this.http.post<Interaction>(this.endpoint, interaction);
  }

  updateInteraction(
    interactionId?: number,
    interaction?: any
  ): Observable<Interaction> {
    return this.http.put<Interaction>(
      `${this.endpoint}/${interactionId}`,
      interaction
    );
  }
}
