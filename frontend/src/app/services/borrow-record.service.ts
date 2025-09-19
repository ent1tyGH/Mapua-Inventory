import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface BorrowRecord {
  item: { id: number };
  borrower: {
    serialNumber: string;
    studentName: string;
    studentNumber: string;
  };
  borrowedAt?: Date;
  returnedAt?: Date;
}

@Injectable({
  providedIn: 'root'
})
export class BorrowRecordService {
  private baseUrl = 'http://localhost:8080/borrow-records';

  constructor(private http: HttpClient) {}

  create(record: BorrowRecord): Observable<BorrowRecord> {
    return this.http.post<BorrowRecord>(this.baseUrl, record);
  }
}
