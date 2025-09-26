package mapua.backend.service;

import mapua.backend.entity.DailyReport;
import mapua.backend.repository.DailyReportRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DailyReportService {

    private final DailyReportRepository repository;

    public DailyReportService(DailyReportRepository repository) {
        this.repository = repository;
    }

    public DailyReport saveReport(DailyReport report) {
        return repository.save(report);
    }

    public List<DailyReport> getAllReports() {
        return repository.findAll();
    }
}
