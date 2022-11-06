import { HttpClient, HttpResponse, HttpStatusCode } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Book } from '../model/book';
import { CustomHttpResponse } from '../model/custom-http-response';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  host: string = 'http://localhost:8081';

  constructor(private http: HttpClient, private authenticationService: AuthenticationService) { }

  getBooks(page: number, size: number): Observable<any> {
    return this.http.get<any>(`${this.host}/book/get/${page}/${size}`);
  }

  addNewBook(book: Book): Observable<Book> {
    return this.http.post<Book>(`${this.host}/book/add`, book);
  }

  updateBook(book: Book): Observable<Book> {
    return this.http.post<Book>(`${this.host}/book/update`, book);
  }

  deleteBook(callNumber: string): Observable<CustomHttpResponse> {
    return this.http.delete<CustomHttpResponse>(`${this.host}/book/delete/${callNumber}`)
  }
}
