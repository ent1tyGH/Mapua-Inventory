package mapua.backend.controller;

import mapua.backend.entity.BorrowRecord;
import mapua.backend.entity.Item;
import mapua.backend.entity.Borrower;
import mapua.backend.service.BorrowRecordService;
import mapua.backend.service.ItemService;
import mapua.backend.service.BorrowerService;
import mapua.backend.dto.BorrowRecordRequest;
import mapua.backend.dto.ReturnRequest;
import mapua.backend.dto.BorrowRecordResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
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
    public List<BorrowRecordResponse> getAllBorrowRecords() {
        return borrowRecordService.getAllBorrowRecords().stream()
                .map(record -> new BorrowRecordResponse(
                        record.getId(),
                        record.getItem() != null && record.getItem().getEquipmentType() != null
                                ? record.getItem().getEquipmentType().getName()  // ✅ fixed
                                : null,
                        record.getBorrower() != null ? record.getBorrower().getStudentName() : null,
                        record.getBorrowedAt(),
                        record.getReturnedAt(),
                        record.getRemarks()
                ))
                .collect(java.util.stream.Collectors.toList());
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

    @PostMapping("/{itemId}/return")
    public ResponseEntity<?> returnBorrowRecord(
            @PathVariable Long itemId,
            @RequestBody ReturnRequest request) {

        try {
            BorrowRecord updated = borrowRecordService.completeReturn(itemId, request);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBorrowRecord(@PathVariable Long id) {
        borrowRecordService.deleteBorrowRecord(id);
        return ResponseEntity.ok().build();
    }
}
