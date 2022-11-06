import { HttpClient, HttpEvent, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Book } from '../model/book';
import { CustomCollection } from '../model/custom-collection';
import { CustomCollectionResponse } from '../model/custom-collection-response';
import { CustomHttpResponse } from '../model/custom-http-response';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class ListService {
  host: string = 'http://localhost:8081';

  constructor(private http: HttpClient,
              private authenticationService: AuthenticationService) { }

  searchBySearchQuery(searchQuery: string): Observable<CustomCollection[]> {
    return this.http.get<CustomCollection[]>(`${this.host}/list/search`, { headers: new HttpHeaders({
      'Search-Query': searchQuery,
      'username': this.authenticationService.getUserFromLocalStorage().username
    })});
  }

  getLists(): Observable<CustomCollectionResponse[]> {
    return this.http.get<CustomCollectionResponse[]>(`${this.host}/list/get`);
  }

  addNewList(listName: string): Observable<CustomHttpResponse> {
    return this.http.post<CustomHttpResponse>(`${this.host}/list/add-list/${listName}`, undefined);
  }

  deleteList(list: string): Observable<CustomHttpResponse> {
    return this.http.delete<CustomHttpResponse>(`${this.host}/list/delete-list/${list}`);
  }

  addBookToList(list: string, book: string): Observable<CustomHttpResponse> {
    return this.http.post<CustomHttpResponse>(`${this.host}/list/add-book/${list}/${book}`, undefined);
  }

  deleteBookFromList(list: string, book: string): Observable<CustomHttpResponse> {
    return this.http.delete<CustomHttpResponse>(`${this.host}/list/delete-book/${list}/${book}`);
  }
}
