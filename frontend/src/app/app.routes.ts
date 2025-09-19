import { Routes } from '@angular/router';
import { EquipmentTypeComponent } from './components/equipment-type/equipment-type.component';
import { ItemComponent } from './components/item/item.component';
import { BorrowComponent } from './components/borrow/borrow.component';

export const routes: Routes = [
  { path: '', redirectTo: '/items', pathMatch: 'full' },
  { path: 'items', component: ItemComponent },
  { path: 'borrow/:id', component: BorrowComponent },
  { path: 'equipment-type/add', component: EquipmentTypeComponent }
];
