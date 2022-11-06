import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Book } from '../model/book';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  host: string = 'http://localhost:8081';

  constructor(private http: HttpClient, private authenticationService: AuthenticationService) { }

  searchByField(searchOption: string, searchQuery: string): Observable<Book[]> {
    return this.http.get<Book[]>(`${this.host}/search/field`, { headers: new HttpHeaders({
      'Search-Field': searchOption,
      'Search-Query': searchQuery,
      'username': this.authenticationService.getUserFromLocalStorage().username
    })});
  }
}
