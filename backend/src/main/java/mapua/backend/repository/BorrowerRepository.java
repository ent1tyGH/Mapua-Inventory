package mapua.backend.repository;

import mapua.backend.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
    Optional<Borrower> findBySerialNumber(String serialNumber);
}
