import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BorrowRecordService } from '../../services/borrow-record.service';

@Component({
  selector: 'app-borrow',
  templateUrl: './borrow.component.html',
  styleUrls: ['./borrow.component.css']
})
export class BorrowComponent {
  itemId!: number;

  serialNumber = '';
  studentName = '';
  studentNumber = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private borrowRecordService: BorrowRecordService
  ) {
    this.itemId = Number(this.route.snapshot.paramMap.get('id'));
  }

  submitBorrow() {
    // Send borrower info and item ID to backend
    const record = {
      item: { id: this.itemId },
      borrower: {
        serialNumber: this.serialNumber,
        studentName: this.studentName,
        studentNumber: this.studentNumber
      }
    };

    this.borrowRecordService.create(record).subscribe({
      next: () => {
        alert('Item borrowed successfully!');
        this.router.navigate(['/items']);
      },
      error: (err) => console.error(err)
    });
  }
}
