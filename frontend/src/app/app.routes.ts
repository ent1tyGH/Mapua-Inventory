import { Routes } from '@angular/router';
import { EquipmentTypeComponent } from './components/equipment-type/equipment-type.component';
import { ItemComponent } from './components/item/item.component';
import { BorrowerComponent } from './components/borrower/borrower.component';
import { ReportsComponent } from './components/reports/reports.component';
import { NewReportComponent } from './components/new-report/new-report.component';


export const routes: Routes = [
  { path: '', redirectTo: '/items', pathMatch: 'full' },
  { path: 'items', component: ItemComponent },
  { path: 'equipment-types', component: EquipmentTypeComponent },
  { path: 'borrowers', component: BorrowerComponent },
  { path: 'reports', component: ReportsComponent },
  { path: 'new-report', component: NewReportComponent },
];
