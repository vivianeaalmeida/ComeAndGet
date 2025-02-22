import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SearchService {
  private searchSubject = new Subject<string>();  // Subject para emitir a palavra-chave

  search$ = this.searchSubject.asObservable();  // Observable que outros componentes podem escutar

  search(keyword: string) {
    this.searchSubject.next(keyword);  // Emite a palavra-chave
  }
}