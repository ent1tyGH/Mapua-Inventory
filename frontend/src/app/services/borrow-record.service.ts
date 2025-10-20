import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BorrowRecord } from '../models/borrow-record.model';

@Injectable({
  providedIn: 'root'
})
export class BorrowRecordService {

  private apiUrl = 'http://localhost:8080/api/borrow-records';

  constructor(private http: HttpClient) {}

  getAll(): Observable<BorrowRecord[]> {
    return this.http.get<BorrowRecord[]>(this.apiUrl);
  }

  getById(id: number): Observable<BorrowRecord> {
    return this.http.get<BorrowRecord>(`${this.apiUrl}/${id}`);
  }

  addBorrowRecord(record: any): Observable<BorrowRecord> {
    return this.http.post<BorrowRecord>(`${this.apiUrl}`, record);
  }

  updateBorrowRecord(id: number, record: BorrowRecord): Observable<BorrowRecord> {
    return this.http.put<BorrowRecord>(`${this.apiUrl}/${id}`, record);
  }

  getBorrowedItemsByBorrowerSerial(serialNumber: string) {
    return this.http.get<any[]>(`${this.apiUrl}/borrower/${serialNumber}`);
  }

  returnItem(itemId: number, payload: any) {
    return this.http.post(`${this.apiUrl}/${itemId}/return`, payload);
  }

  deleteBorrowRecord(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
