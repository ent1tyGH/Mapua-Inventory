import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-equipment-type',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './equipment-type.component.html',
  styleUrls: ['./equipment-type.component.css']
})
export class EquipmentTypeComponent implements OnInit {
  name: string = '';
  types: { id: number; name: string }[] = [];

  private baseUrl = 'http://localhost:8080/items/equipment-types';

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.getTypes();
  }

  getTypes() {
    this.http.get<{ id: number; name: string }[]>(this.baseUrl)
      .subscribe(result => this.types = result);
  }

  addType() {
    console.log('addType() called with name:', this.name);
    if (!this.name.trim()) return;

    this.http.post<{ id: number; name: string }>(this.baseUrl, { name: this.name })
      .subscribe({
        next: (res) => {
          this.types.push(res);
          this.name = '';
        },
        error: (err) => console.error(err)
      });
  }
}
