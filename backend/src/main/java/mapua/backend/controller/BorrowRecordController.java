package mapua.backend.controller;

import mapua.backend.entity.BorrowRecord;
import mapua.backend.entity.Item;
import mapua.backend.entity.Borrower;
import mapua.backend.service.BorrowRecordService;
import mapua.backend.service.ItemService;
import mapua.backend.service.BorrowerService;
import mapua.backend.dto.BorrowRecordRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/borrow-records")
public class BorrowRecordController {

    private final BorrowRecordService borrowRecordService;
    private final ItemService itemService;
    private final BorrowerService borrowerService;

    public BorrowRecordController(BorrowRecordService borrowRecordService, ItemService itemService, BorrowerService borrowerService) {
        this.borrowRecordService = borrowRecordService;
        this.itemService = itemService;
        this.borrowerService = borrowerService;
    }

    @GetMapping
    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowRecordService.getAllBorrowRecords();
    }

    @PostMapping
    public ResponseEntity<BorrowRecord> createBorrowRecord(@RequestBody BorrowRecordRequest request) {
        // fetch the item
        Item item = itemService.getItemById(request.getItemId());
        if (item == null || item.isBorrowed()) {
            return ResponseEntity.badRequest().build();
        }

        // fetch or create borrower
        Borrower borrower = borrowerService.getBorrowerBySerial(request.getSerialNumber());
        if (borrower == null) {
            borrower = new Borrower();
            borrower.setSerialNumber(request.getSerialNumber());
            borrower.setStudentName(request.getStudentName());
            borrower.setStudentNumber(request.getStudentNumber());
            borrower = borrowerService.saveBorrower(borrower);
        }

        // mark item as borrowed
        item.setBorrowed(true);
        itemService.addItem(item);

        // create record
        BorrowRecord record = new BorrowRecord();
        record.setItem(item);
        record.setBorrower(borrower);
        record.setBorrowedAt(LocalDateTime.now());

        BorrowRecord saved = borrowRecordService.saveBorrowRecord(record);
        return ResponseEntity.status(201).body(saved);
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<BorrowRecord> returnBorrowRecord(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> body) {

        BorrowRecord record = borrowRecordService.getBorrowRecordById(id);

        if (record == null || record.getReturnedAt() != null) {
            return ResponseEntity.badRequest().build();
        }

        // mark return time
        record.setReturnedAt(LocalDateTime.now());

        // optional remarks
        if (body != null && body.containsKey("remarks")) {
            record.setRemarks(body.get("remarks"));
        }

        // mark item as available
        Item item = record.getItem();
        item.setBorrowed(false);
        itemService.addItem(item);

        BorrowRecord updated = borrowRecordService.saveBorrowRecord(record);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBorrowRecord(@PathVariable Long id) {
        borrowRecordService.deleteBorrowRecord(id);
        return ResponseEntity.ok().build();
    }
}
