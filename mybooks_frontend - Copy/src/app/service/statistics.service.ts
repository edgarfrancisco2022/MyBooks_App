import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StatisticsResponse } from '../model/statistics-response';

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {
  host: string = 'http://localhost:8081';

  constructor(private http: HttpClient) { }

  getStatistics(): Observable<StatisticsResponse> {
    return this.http.get<StatisticsResponse>(`${this.host}/statistics/get`);
  }
}
