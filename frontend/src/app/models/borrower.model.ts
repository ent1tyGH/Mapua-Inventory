export interface Borrower {
  id?: number;           // internal PK from DB
  serialNumber: string;  // NFC/manual input
  studentName: string;
  studentNumber: string;
}
