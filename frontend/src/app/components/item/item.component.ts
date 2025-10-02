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

// State properties for editing
selectedItem: Item | null = null;
isEditing: boolean = false;

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

  editItem(item: Item): void {
    // Create a deep copy of the item
    this.selectedItem = JSON.parse(JSON.stringify(item));
    this.isEditing = true;
  }

  /**
   * Logic to clean the payload and update the item via the service.
   */
  updateItem() {
    if (!this.selectedItem || !this.selectedItem.id) return;

    // 1. Create a DTO (Data Transfer Object) for the PUT request
    const payload: Item = {
        ...this.selectedItem,
        // Ensure equipmentType only contains the ID for a clean backend update
        equipmentType: {
            id: this.selectedItem.equipmentType.id,
            name: this.selectedItem.equipmentType.name
        }
    };

    // 2. Call the service's update method
    this.itemService.update(payload).subscribe({
      next: (updatedItem) => {
        // 3. SUCCESS: Find the item in the local list and update it
        const index = this.items.findIndex(i => i.id === updatedItem.id);
        if (index !== -1) {
          this.items[index] = updatedItem;
        }

        // 4. Reset the state and close the form
        this.cancelEdit();
      },
      error: (err) => {
        // This block executes if the service call fails (e.g., 400 or 500 error)
        console.error('Error updating item:', err);
        // Display the error message
        alert('Failed to update item. Please check the console for details.');
      }
    });
  }

  cancelEdit(): void {
    this.isEditing = false;
    this.selectedItem = null;
  }

  borrowItem(item: any) {
    console.log("Borrowing item:", item);
  }
}
