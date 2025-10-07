import { Borrower } from './borrower.model';

export interface BorrowRecord {
  id?: number;
  borrower: Borrower;
  item: {
    id: number;
    equipmentType: {
      id: number;
      name: string;
    };
    specifications: string;
    location: string;
    conditionStatus: string;
    borrowed: boolean;
  };
  borrowedAt: string;
  returnedAt: string | null;
  remarks: string;
}
