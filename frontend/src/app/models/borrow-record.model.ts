import { Borrower } from './borrower.model';

export interface BorrowRecord {
  id?: number;
  borrower: Borrower;
  item: {
    id: number;
  };
  borrowedAt: string;
  returnedAt: string | null;
  remarks: string;
}
