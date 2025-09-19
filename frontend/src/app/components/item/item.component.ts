import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { ItemService, Item, BorrowRecord } from '../../services/item.service';
import { EquipmentTypeService, EquipmentType } from '../../services/equipment-type.service';

@Component({
  selector: 'app-item',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css']
})
export class ItemComponent implements OnInit {
  items: Item[] = [];
  equipmentTypes: EquipmentType[] = [];

  newItem: Item = {
    equipmentType: { id: 0, name: '' },
    specifications: '',
    location: '',
    conditionStatus: 'WORKING',
    borrowed: false
  };

  hoveredItem: number | null = null;

  constructor(
    private itemService: ItemService,
    private equipmentTypeService: EquipmentTypeService
  ) {}

  ngOnInit() {
    this.loadItems();
    this.loadEquipmentTypes();
  }

  loadItems() {
    this.itemService.getAll().subscribe(result => {
      this.items = result.map(i => ({
        ...i,
        equipmentType: i.equipmentType ?? { id: 0, name: '' },
        borrowRecords: i.borrowRecords ?? []
      }));
    });
  }

  loadEquipmentTypes() {
    this.equipmentTypeService.getAll().subscribe(result => this.equipmentTypes = result);
  }

  addItem() {
    this.itemService.create(this.newItem).subscribe({
      next: (res) => {
        this.items.push({
          ...res,
          equipmentType: res.equipmentType ?? { id: 0, name: '' },
          borrowRecords: res.borrowRecords ?? []
        });
        this.newItem = {
          equipmentType: { id: 0, name: '' },
          specifications: '',
          location: '',
          conditionStatus: 'WORKING',
          borrowed: false
        };
      },
      error: (err) => console.error(err)
    });
  }

  borrowItem(item: Item) {
    if (!item.id) return;
    this.itemService.borrow(item.id).subscribe({
      next: (updatedItem) => {
        const index = this.items.findIndex(i => i.id === updatedItem.id);
        if (index !== -1) this.items[index] = updatedItem;
      },
      error: (err) => console.error(err)
    });
  }

  returnItem(item: Item) {
    if (!item.id) return;
    this.itemService.return(item.id).subscribe({
      next: (updatedItem) => {
        const index = this.items.findIndex(i => i.id === updatedItem.id);
        if (index !== -1) this.items[index] = updatedItem;
      },
      error: (err) => console.error(err)
    });
  }
}
