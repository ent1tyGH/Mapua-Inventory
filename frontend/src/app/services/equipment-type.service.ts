// src/app/services/equipment-type.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface EquipmentType {
  id?: number;
  name: string;
}

@Injectable({
  providedIn: 'root'
})
export class EquipmentTypeService {
  private baseUrl = 'http://localhost:8080/items/equipment-types';

  constructor(private http: HttpClient) { }

  getAll(): Observable<EquipmentType[]> {
    return this.http.get<EquipmentType[]>(this.baseUrl);
  }

  create(type: EquipmentType): Observable<EquipmentType> {
    return this.http.post<EquipmentType>(this.baseUrl, type);
  }
}
