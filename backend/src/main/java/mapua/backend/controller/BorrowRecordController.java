package mapua.backend.controller;

import mapua.backend.entity.BorrowRecord;
import mapua.backend.entity.Item;
import mapua.backend.service.BorrowRecordService;
import mapua.backend.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/borrow-records")
public class BorrowRecordController {

    private final BorrowRecordService borrowRecordService;
    private final ItemService itemService;

    public BorrowRecordController(BorrowRecordService borrowRecordService, ItemService itemService) {
        this.borrowRecordService = borrowRecordService;
        this.itemService = itemService;
    }

    @GetMapping
    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowRecordService.getAllBorrowRecords();
    }

    @PostMapping
    public ResponseEntity<BorrowRecord> createBorrowRecord(@RequestBody BorrowRecord record) {
        // fetch the item
        Item item = itemService.getItemById(record.getItem().getId());

        if (item == null || item.isBorrowed()) {
            return ResponseEntity.badRequest().build(); // item not found or already borrowed
        }

        // mark item as borrowed
        item.setBorrowed(true);
        itemService.addItem(item);

        record.setItem(item);
        BorrowRecord saved = borrowRecordService.saveBorrowRecord(record);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<BorrowRecord> returnBorrowRecord(@PathVariable Long id) {
        BorrowRecord record = borrowRecordService.getBorrowRecordById(id);

        if (record == null || record.getReturnedAt() != null) {
            return ResponseEntity.badRequest().build(); // record not found or already returned
        }

        // mark return time
        record.setReturnedAt(LocalDateTime.now());

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
