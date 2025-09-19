import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EquipmentType } from './equipment-type.service';
import { BorrowRecord } from './borrow-record.service';

export interface Item {
  id?: number;
  equipmentType: EquipmentType;
  specifications: string;
  location: string;
  conditionStatus: string;
  borrowed: boolean;
  borrowRecords?: BorrowRecord[]; // <-- added for linking borrower data
}

@Injectable({
  providedIn: 'root'
})
export class ItemService {
  private baseUrl = 'http://localhost:8080/items'; // Adjust if backend differs

  constructor(private http: HttpClient) {}

  getAll(): Observable<Item[]> {
    return this.http.get<Item[]>(this.baseUrl);
  }

  create(item: Item): Observable<Item> {
    return this.http.post<Item>(this.baseUrl, item);
  }

  borrow(id: number): Observable<Item> {
    return this.http.put<Item>(`${this.baseUrl}/${id}/borrow`, {});
  }

  return(id: number): Observable<Item> {
    return this.http.put<Item>(`${this.baseUrl}/${id}/return`, {});
  }
}
