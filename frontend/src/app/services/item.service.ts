// src/app/services/item.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EquipmentType } from './equipment-type.service';

export interface Item {
id?: number;
equipmentType: EquipmentType;
specifications: string;
location: string;
conditionStatus: string;
borrowed: boolean;
}

@Injectable({
providedIn: 'root'
})
export class ItemService {
private baseUrl = 'http://localhost:8080/api/items'; // Adjust if your backend uses a different path

constructor(private http: HttpClient) {}

  getAll(): Observable<Item[]> {
    return this.http.get<Item[]>(this.baseUrl);
  }

  create(item: Item): Observable<Item> {
    return this.http.post<Item>(this.baseUrl, item);
  }

  /**
   * FIX: Added the 'update' method to send the item data to the backend API.
   */
  update(item: Item): Observable<Item> {
    // Uses the item ID to target the correct resource on the backend
    const url = `${this.baseUrl}/${item.id}`;
    return this.http.put<Item>(url, item);
  }
}
