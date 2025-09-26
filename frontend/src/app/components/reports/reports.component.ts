import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule, DatePipe } from '@angular/common';
import { DailyReportService } from '../../services/daily-report.service';
import { DailyReport } from '../../models/daily-report.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-reports',
  standalone: true,
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css'],
  imports: [CommonModule, FormsModule, DatePipe]
})
export class ReportsComponent implements OnInit {
  reports: DailyReport[] = [];

  constructor(
    private reportService: DailyReportService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadReports();
  }

  loadReports(): void {
    this.reportService.getReports().subscribe(data => {
      this.reports = data.sort((a, b) => {
        const dateA = a.createdAt ? new Date(a.createdAt).getTime() : 0;
        const dateB = b.createdAt ? new Date(b.createdAt).getTime() : 0;
        return dateB - dateA;
      });
    });
  }


  goToNewReport(): void {
    this.router.navigate(['/new-report']);
  }
}
