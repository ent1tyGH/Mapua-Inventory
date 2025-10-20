package mapua.backend.service;

import mapua.backend.entity.BorrowRecord;
import mapua.backend.entity.Item;
import mapua.backend.repository.BorrowRecordRepository;
import mapua.backend.repository.ItemRepository;
import mapua.backend.dto.ReturnRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BorrowRecordService {

    private final BorrowRecordRepository borrowRecordRepository;
    private final ItemRepository itemRepository; // ✅ Inject item repo

    public BorrowRecordService(BorrowRecordRepository borrowRecordRepository, ItemRepository itemRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.itemRepository = itemRepository;
    }

    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowRecordRepository.findAll();
    }

    public BorrowRecord getBorrowRecordById(Long id) {
        return borrowRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BorrowRecord not found with id: " + id));
    }

    public BorrowRecord saveBorrowRecord(BorrowRecord record) {
        return borrowRecordRepository.save(record);
    }

    public void deleteBorrowRecord(Long id) {
        borrowRecordRepository.deleteById(id);
    }

    public BorrowRecord completeReturn(Long itemId, ReturnRequest request) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + itemId));

        BorrowRecord record = borrowRecordRepository.findByItemAndReturnedAtIsNull(item)
                .orElseThrow(() -> new RuntimeException("No active borrow record found for this item"));

        if (!record.getBorrower().getSerialNumber().equals(request.getSerialNumber())) {
            throw new RuntimeException("Invalid borrower: this item was not borrowed by the provided serial number");
        }

        record.setReturnedAt(LocalDateTime.now());

        if (request.getRemarks() != null && !request.getRemarks().isBlank()) {
            record.setRemarks(request.getRemarks());
        }

        item.setBorrowed(false);

        if (request.getConditionStatus() != null) {
            item.setConditionStatus(request.getConditionStatus());
        }

        itemRepository.save(item);
        return borrowRecordRepository.save(record);
    }

    public List<BorrowRecord> getActiveBorrowRecordsByBorrower(Long borrowerId) {
        return borrowRecordRepository.findByBorrowerIdAndReturnedAtIsNull(borrowerId);
    }
}
