package mapua.backend.repository;

import mapua.backend.entity.DailyReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyReportRepository extends JpaRepository<DailyReport, Long> {
}
