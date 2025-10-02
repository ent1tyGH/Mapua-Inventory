import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DailyReportService } from '../../services/daily-report.service';
import { DailyReport } from '../../models/daily-report.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-new-report',
  standalone: true,
  templateUrl: './new-report.component.html',
  imports: [CommonModule, FormsModule],
})
export class NewReportComponent {
  reportText: string = '';

  constructor(private reportService: DailyReportService, private router: Router) {}

  submitReport(): void {
    const report: DailyReport = { reportText: this.reportText };
    this.reportService.addReport(report).subscribe(() => {
      this.router.navigate(['/reports']);
    });
  }

  goBack(): void {
      this.router.navigate(['/reports']);
    }
}
