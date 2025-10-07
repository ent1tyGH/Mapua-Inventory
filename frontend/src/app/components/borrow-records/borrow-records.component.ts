import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

interface BorrowRecordResponse {
  id: number;
  itemName: string;
  borrowerName: string;
  borrowedAt: string;
  returnedAt: string | null;
  remarks: string | null;
}

@Component({
  selector: 'app-borrow-records',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './borrow-records.component.html'
})
export class BorrowRecordsComponent implements OnInit {
  records: BorrowRecordResponse[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.http.get<BorrowRecordResponse[]>('http://localhost:8080/api/borrow-records')
      .subscribe({
        next: (data) => this.records = data,
        error: (err) => console.error('Failed to load records', err)
      });
  }
}
