import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DailyReport } from '../models/daily-report.model';

@Injectable({
  providedIn: 'root'
})
export class DailyReportService {
  private apiUrl = 'http://localhost:8080/api/reports';

  constructor(private http: HttpClient) {}

  getReports(): Observable<DailyReport[]> {
    return this.http.get<DailyReport[]>(this.apiUrl);
  }

  addReport(report: DailyReport): Observable<DailyReport> {
    return this.http.post<DailyReport>(this.apiUrl, report);
  }
}
