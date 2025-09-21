import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Borrower } from '../models/borrower.model';

@Injectable({
  providedIn: 'root'
})
export class BorrowerService {
  private apiUrl = 'http://localhost:8080/api/borrowers';

  constructor(private http: HttpClient) {}

  getBorrowers(): Observable<Borrower[]> {
    return this.http.get<Borrower[]>(this.apiUrl);
  }

  addBorrower(borrower: Borrower): Observable<Borrower> {
    return this.http.post<Borrower>(this.apiUrl, borrower);
  }

  updateBorrower(id: number, borrower: Borrower): Observable<Borrower> {
    return this.http.put<Borrower>(`${this.apiUrl}/${id}`, borrower);
  }

  deleteBorrower(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getBorrowerBySerial(serialNumber: string): Observable<Borrower> {
    return this.http.get<Borrower>(`${this.apiUrl}/serial/${serialNumber}`);
  }

}
