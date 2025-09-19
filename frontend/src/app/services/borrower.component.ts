import { Component, OnInit } from '@angular/core';
import { BorrowerService, Borrower } from '../services/borrower.service';

@Component({
selector: 'app-borrower',
templateUrl: './borrower.component.html',
styleUrls: ['./borrower.component.css']
})
export class BorrowerComponent implements OnInit {
borrowers: Borrower[] = [];
newBorrower: Borrower = { borrower_id: 0, item_id: 0, borrowed_at: '' };

constructor(private borrowerService: BorrowerService) {}

  ngOnInit(): void {
    this.loadBorrowers();
  }

  loadBorrowers() {
    this.borrowerService.getBorrowers().subscribe(data => {
      this.borrowers = data;
    });
  }

  addBorrower() {
    this.borrowerService.addBorrower(this.newBorrower).subscribe(() => {
      this.loadBorrowers();
      this.newBorrower = { borrower_id: 0, item_id: 0, borrowed_at: '' }; // reset form
    });
  }
}
