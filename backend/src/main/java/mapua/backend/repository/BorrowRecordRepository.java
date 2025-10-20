package mapua.backend.repository;

import mapua.backend.entity.BorrowRecord;
import mapua.backend.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    Optional<BorrowRecord> findByItemAndReturnedAtIsNull(Item item);

    List<BorrowRecord> findByBorrowerIdAndReturnedAtIsNull(Long borrowerId);
}
