import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BorrowerService } from '../../services/borrower.service';
import { BorrowRecordService } from '../../services/borrow-record.service';
import { Borrower } from '../../models/borrower.model';
import { BorrowRecord } from '../../models/borrow-record.model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-borrower',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './borrower.component.html',
  styleUrls: ['./borrower.component.css']
})
export class BorrowerComponent {
  serialNumber: string = '';
  showNewBorrowerForm: boolean = false;
  successMessage: string = '';
  itemId?: number; // set this when navigating to borrow page

  newBorrower: Borrower = {
    id: undefined,
    serialNumber: '',
    studentName: '',
    studentNumber: ''
  };

  constructor(
    private borrowerService: BorrowerService,
    private borrowRecordService: BorrowRecordService,
    private route: ActivatedRoute
  ) {
    this.route.queryParams.subscribe((params: { [key: string]: any }) => {
      this.itemId = params['itemId'] ? +params['itemId'] : undefined;
    });
  }

  ngOnInit() {
      // pull itemId from query params
      this.route.queryParams.subscribe(params => {
        if (params['itemId']) {
          this.itemId = +params['itemId']; // convert string → number
          console.log('Borrowing item with id:', this.itemId);
        }
      });
    }

  // Check if borrower exists
  checkBorrower() {
    if (!this.serialNumber) return;

    this.borrowerService.getBorrowers().subscribe(borrowers => {
      const existing = borrowers.find(
        b => b.serialNumber === this.serialNumber
      );

      if (existing) {
        this.successMessage = `Borrower ${existing.studentName} found.`;
        this.showNewBorrowerForm = false;
        this.createBorrowRecord(existing);
      } else {
        this.showNewBorrowerForm = true;
      }
    });
  }

  // Save new borrower
  saveBorrower() {
    if (!this.serialNumber || !this.newBorrower.studentName || !this.newBorrower.studentNumber) return;

    const borrowerToSave: Borrower = {
      ...this.newBorrower,
      serialNumber: this.serialNumber,
      id: undefined // let Spring Boot generate the ID
    };

    this.borrowerService.addBorrower(borrowerToSave).subscribe({
      next: savedBorrower => {
        // Show success message
        this.successMessage = `Borrower ${savedBorrower.studentName} added successfully!`;

        // Hide the new borrower form
        this.showNewBorrowerForm = false;

        // Reset form
        this.newBorrower = { studentName: '', studentNumber: '', serialNumber: '' };
        this.serialNumber = '';

        // Automatically hide the message after 3 seconds
        setTimeout(() => this.successMessage = '', 3000);

        // Create borrow record
        this.createBorrowRecord(savedBorrower);
      },
      error: err => {
        console.error('Error saving borrower', err);
        this.successMessage = `Error saving borrower. Please try again.`;
        setTimeout(() => this.successMessage = '', 3000);
      }
    });
  }

  // Create borrow record for an item
  createBorrowRecord(borrower: Borrower) {
    if (!this.itemId) return;

    // Build record payload for backend
    const record: BorrowRecord = {
      borrower: {
        id: borrower.id,                      // may be undefined if new
        serialNumber: borrower.serialNumber,  // always required
        studentName: borrower.studentName,    // only needed if new
        studentNumber: borrower.studentNumber // only needed if new
      },
      item: { id: this.itemId },              // backend needs item object with id
      borrowedAt: new Date().toISOString(),
      returnedAt: null,
      remarks: ''
    };

    console.log('Sending record to backend:', record);

    this.borrowRecordService.addBorrowRecord(record).subscribe({
      next: saved => {
        this.successMessage += ` Item successfully borrowed!`;
        // hide message after 3s
        setTimeout(() => this.successMessage = '', 3000);
      },
      error: err => {
        console.error('Error creating borrow record', err);
        this.successMessage = 'Error borrowing item. Please try again.';
        setTimeout(() => this.successMessage = '', 3000);
      }
    });
  }
}
