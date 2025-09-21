import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ItemService, Item } from '../../services/item.service';
import { EquipmentTypeService, EquipmentType } from '../../services/equipment-type.service';
import { RouterModule } from '@angular/router';

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

  constructor(
    private itemService: ItemService,
    private equipmentTypeService: EquipmentTypeService
  ) {}

  ngOnInit() {
    this.loadItems();
    this.loadEquipmentTypes();
  }

  loadItems() {
    this.itemService.getAll().subscribe(result => this.items = result);
  }

  loadEquipmentTypes() {
    this.equipmentTypeService.getAll().subscribe(result => this.equipmentTypes = result);
  }

  addItem() {
    this.itemService.create(this.newItem).subscribe({
      next: (res) => {
        this.items.push(res);
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

  borrowItem(item: any) {
    // Navigate to borrowers form or call your borrow service
    console.log("Borrowing item:", item);
    // Example: this.router.navigate(['/borrow', item.id]);
  }

}
