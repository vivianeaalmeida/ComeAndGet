import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category } from '../Models/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  endpoint = 'http://localhost:8080/api/v1/categories';

  constructor(private http: HttpClient) {}

  getCategories():Observable<Category[]> {
    return this.http.get<Category[]>(this.endpoint)
  }

  addCategory(category:Category):Observable<Category> {
    return this.http.post<Category>(this.endpoint, category)
  }

  updateCategory(id:string, category:Category):Observable<Category> {
    return this.http.put<Category>(`${this.endpoint}/${id}`, category)
  }

  deleteCategory(id:string):Observable<Category> {
    return this.http.delete<Category>(`${this.endpoint}/${id}`)
  }
}