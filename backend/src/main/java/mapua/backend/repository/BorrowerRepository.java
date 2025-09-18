package mapua.backend.repository;

import mapua.backend.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
}
