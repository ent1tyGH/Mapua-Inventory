import { Routes } from '@angular/router';
import { EquipmentTypeComponent } from './components/equipment-type/equipment-type.component';
import { ItemComponent } from './components/item/item.component';
import { BorrowerComponent } from './components/borrower/borrower.component';


export const routes: Routes = [
  { path: '', redirectTo: '/items', pathMatch: 'full' },
  { path: 'items', component: ItemComponent },
  { path: 'equipment-types', component: EquipmentTypeComponent },
  { path: 'borrowers', component: BorrowerComponent },
];
