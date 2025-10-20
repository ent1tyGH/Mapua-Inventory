import { Component } from '@angular/core';
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
export class ReturnComponent {
  serialNumber = '';
  borrowedItems: any[] | null = null;
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(private borrowRecordService: BorrowRecordService) {}

  fetchBorrowedItems() {
    this.successMessage = this.errorMessage = null;
    this.borrowRecordService.getBorrowedItemsByBorrowerSerial(this.serialNumber).subscribe({
      next: (data) => {
        this.borrowedItems = data;
        if (!data || data.length === 0) this.errorMessage = 'No borrowed items found.';
      },
      error: () => this.errorMessage = 'Error fetching borrowed items.'
    });
  }

  // ✅ This is the returnItem() function used in your HTML
  returnItem(itemId: number, record: any) {
    if (!record.remarks || record.remarks.trim() === '') {
      alert('Remarks are required.');
      return;
    }

    if (!record.conditionStatus) {
      alert('Please select a condition status.');
      return;
    }

    const payload = {
      serialNumber: this.serialNumber,
      remarks: record.remarks,
      conditionStatus: record.conditionStatus
    };

    this.borrowRecordService.returnItem(itemId, payload).subscribe({
      next: () => {
        alert('Item successfully returned and condition updated!');
        this.borrowedItems = this.borrowedItems?.filter(i => i.item.id !== itemId) || null;
        if (!this.borrowedItems?.length) this.resetForm();
      },
      error: (err) => {
        console.error('Error returning item:', err);
        alert('Failed to return item. Please try again.');
      }
    });
  }

  resetForm() {
    this.serialNumber = '';
    this.borrowedItems = null;
    this.successMessage = this.errorMessage = null;
  }
}
