package mapua.backend.controller;

import mapua.backend.entity.DailyReport;
import mapua.backend.service.DailyReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class DailyReportController {

    private final DailyReportService service;

    public DailyReportController(DailyReportService service) {
        this.service = service;
    }

    @PostMapping
    public DailyReport createReport(@RequestBody DailyReport report) {
        return service.saveReport(report);
    }

    @GetMapping
    public List<DailyReport> getReports() {
        return service.getAllReports();
    }
}
