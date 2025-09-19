package mapua.backend.service;

import mapua.backend.entity.Borrower;
import mapua.backend.repository.BorrowerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowerService {

    private final BorrowerRepository borrowerRepository;

    public BorrowerService(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    public List<Borrower> getAllBorrowers() {
        return borrowerRepository.findAll();
    }

    public Borrower saveBorrower(Borrower borrower) {
        return borrowerRepository.save(borrower);
    }

    public void deleteBorrower(Long id) {
        borrowerRepository.deleteById(id);
    }

    // --- new method ---
    public Borrower findBySerialNumber(String serialNumber) {
        return borrowerRepository.findBySerialNumber(serialNumber).orElse(null);
    }
}
