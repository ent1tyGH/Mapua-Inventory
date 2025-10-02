import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BorrowRecordService } from '../../services/borrow-record.service';

@Component({
  selector: 'app-return',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './return.component.html',
  styleUrls: ['./return.component.css']
})
export class ReturnComponent implements OnInit {
  itemId!: number;
  serialNumber: string = '';
  remarks: string = '';

  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private borrowRecordService: BorrowRecordService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.itemId = +params['itemId'];
    });
  }

  submitReturn() {
    const payload = {
      serialNumber: this.serialNumber, // ✅ keep this for borrower validation
      remarks: this.remarks
    };

    console.log('📤 Sending return request:', {
        itemId: this.itemId,
        payload
      });

    this.borrowRecordService.returnItem(this.itemId, payload).subscribe({
      next: () => {
        alert('Item successfully returned!');
        this.router.navigate(['/items']);
      },
      error: (err) => {
        console.error('Error returning item:', err);
        alert('Failed to return item. Please try again.');
      }
    });
  }
}
