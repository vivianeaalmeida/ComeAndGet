import { HttpClient, HttpParams } from '@angular/common/http';
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

  // Deactivate advertisement
  deactivateAdvertisement(id: string): Observable<Advertisement> {
    return this.myWeb.patch<Advertisement>(
      `${this.endpoint}advertisements/${id}/deactivate`,
      {}
    );
  }

  /**
   * Retrieves a list of active advertisements based on search filters
   * 
   * @param keyword      A keyword to search advertisements whose title or description contain the specified text (optional).
   * @param municipality The municipality to filter the advertisements (optional).
   * @param category     The category to filter the advertisements (optional).
   * @return An Observable containing a list of active advertisements matching the provided criteria
   */
  searchAdvertisement(keyword?: string, municipality?: string, category?: string): Observable<Advertisement[]> {
    let params = new HttpParams();

    if (keyword) {
      params = params.append('keyword', keyword);
    }
    if (municipality) {
      params = params.append('municipality', municipality);
    }
    if (category) {
      params = params.append('category', category);
    }

    return this.myWeb.get<Advertisement[]>(`${this.endpoint}advertisements/active/search`, { params });
  }
}
