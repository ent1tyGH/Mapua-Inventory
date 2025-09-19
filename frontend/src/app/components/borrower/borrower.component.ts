import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
selector: 'app-borrower',
standalone: true,
imports: [CommonModule, FormsModule],
templateUrl: './borrower.component.html',
styleUrls: ['./borrower.component.css']
})
export class BorrowerComponent {
newBorrower = {
borrowerId: null,
itemId: null,
borrowedAt: '',
returnedAt: '',
remarks: ''
};

borrowers: any[] = [];

addBorrowerRecord() {
    this.borrowers.push({ ...this.newBorrower });
    this.newBorrower = { borrowerId: null, itemId: null, borrowedAt: '', returnedAt: '', remarks: '' };
  }
}
