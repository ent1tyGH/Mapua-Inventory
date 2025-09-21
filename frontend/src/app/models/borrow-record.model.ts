export interface BorrowRecord {
  borrowerId: number;
  itemId: number;
  borrowedAt: string;
  returnedAt?: string | null;
  remarks?: string;
}
