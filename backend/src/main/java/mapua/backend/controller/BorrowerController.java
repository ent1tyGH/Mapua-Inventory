package mapua.backend.controller;

import mapua.backend.entity.Borrower;
import mapua.backend.service.BorrowerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowers")
public class BorrowerController {

    private final BorrowerService borrowerService;

    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @GetMapping
    public List<Borrower> getAllBorrowers() {
        return borrowerService.getAllBorrowers();
    }

    @GetMapping("/serial/{serialNumber}")
    public ResponseEntity<Borrower> getBorrowerBySerial(@PathVariable String serialNumber) {
        Borrower borrower = borrowerService.getBorrowerBySerial(serialNumber);
        if (borrower == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(borrower);
    }

    @PostMapping
    public Borrower createBorrower(@RequestBody Borrower borrower) {
        return borrowerService.saveBorrower(borrower);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBorrower(@PathVariable Long id) {
        borrowerService.deleteBorrower(id);
        return ResponseEntity.ok().build();
    }
}

