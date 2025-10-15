import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { ItemService, Item } from '../../services/item.service';
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
filteredItems: Item[] = [];
equipmentTypes: EquipmentType[] = [];
newItem: Item = {
id: 0,
equipmentType: { id: 0, name: '' },
specifications: '',
location: '',
conditionStatus: 'WORKING',
borrowed: false
};

isEditing: boolean = false;

// ✅ Search/filter variables
searchText: string = '';
showAvailable: boolean = false;
showBorrowed: boolean = false;

// ✅ Condition filters
filterWorking: boolean = false;
filterNeedsRepair: boolean = false;
filterDisposed: boolean = false;

// ✅ Dashboard data
selectedTypeId: number = 0;
dashboardData = {
total: 0,
available: 0,
borrowed: 0
};

constructor(
    private itemService: ItemService,
    private equipmentTypeService: EquipmentTypeService
  ) {}

  ngOnInit(): void {
    this.loadItems();
    this.loadEquipmentTypes();
  }

  // ✅ Load all items
  loadItems(): void {
    this.itemService.getAll().subscribe((data) => {
      this.items = data;
      this.applyFilter();     // Initialize filtered list
      this.updateDashboard(); // Update dashboard stats
    });
  }

  loadEquipmentTypes(): void {
    this.equipmentTypeService.getAll().subscribe((types) => {
      this.equipmentTypes = types;
    });
  }

  addItem(): void {
    const payload = {
      equipmentType: { id: this.newItem.equipmentType.id },
      specifications: this.newItem.specifications,
      location: this.newItem.location,
      conditionStatus: this.newItem.conditionStatus,
      borrowed: this.newItem.borrowed
    };

    this.itemService.create(payload as any).subscribe({
      next: () => {
        this.loadItems(); // refresh dashboard and filters automatically
        this.resetForm();
      },
      error: (err) => {
        console.error('Error adding item:', err);
        alert('Failed to add item. Check console for details.');
      }
    });
  }

  editItem(item: Item): void {
    this.newItem = { ...item };
    this.isEditing = true;
  }

  updateItem(): void {
    this.itemService.update(this.newItem).subscribe({
      next: () => {
        this.loadItems();
        this.cancelEdit();
      },
      error: (err) => console.error('Error updating item:', err)
    });
  }

  cancelEdit(): void {
    this.isEditing = false;
    this.resetForm();
  }

  resetForm(): void {
    this.newItem = {
      id: 0,
      equipmentType: { id: 0, name: '' },
      specifications: '',
      location: '',
      conditionStatus: 'WORKING',
      borrowed: false
    };
  }

  // ✅ Search + Filter Logic
  applyFilter(): void {
    this.filteredItems = this.items.filter((item) => {
      const matchesSearch =
        !this.searchText ||
        item.equipmentType?.name.toLowerCase().includes(this.searchText.toLowerCase()) ||
        item.specifications.toLowerCase().includes(this.searchText.toLowerCase()) ||
        item.location.toLowerCase().includes(this.searchText.toLowerCase());

      const matchesAvailability =
        (!this.showAvailable && !this.showBorrowed) ||
        (this.showAvailable && !item.borrowed) ||
        (this.showBorrowed && item.borrowed);

      const matchesCondition =
        (!this.filterWorking && !this.filterNeedsRepair && !this.filterDisposed) ||
        (this.filterWorking && item.conditionStatus === 'WORKING') ||
        (this.filterNeedsRepair && item.conditionStatus === 'NEEDS_REPAIR') ||
        (this.filterDisposed && item.conditionStatus === 'DISPOSED');

      return matchesSearch && matchesAvailability && matchesCondition;
    });
  }

  // ✅ Dashboard Update Logic
  updateDashboard(): void {
    let filtered = this.items;

    // Filter by selected equipment type
    if (this.selectedTypeId && this.selectedTypeId !== 0) {
      filtered = filtered.filter(item => item.equipmentType?.id === this.selectedTypeId);
    }

    const total = filtered.length;
    const borrowed = filtered.filter(item => item.borrowed).length;
    const available = total - borrowed;

    this.dashboardData = {
      total,
      available,
      borrowed
    };
  }
}
