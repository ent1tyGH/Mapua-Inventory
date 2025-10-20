package mapua.backend.dto;

import java.time.LocalDateTime;

public class BorrowRecordResponse {
    private Long id;
    private String itemName;
    private Long itemId;
    private String borrowerName;
    private LocalDateTime borrowedAt;
    private LocalDateTime returnedAt;
    private String remarks;

    public BorrowRecordResponse(Long id, String itemName, Long itemId, String borrowerName,
                                LocalDateTime borrowedAt, LocalDateTime returnedAt, String remarks) {
        this.id = id;
        this.itemName = itemName;
        this.itemId = itemId;
        this.borrowerName = borrowerName;
        this.borrowedAt = borrowedAt;
        this.returnedAt = returnedAt;
        this.remarks = remarks;
    }

    // Getters only (no need for setters)
    public Long getId() { return id; }
    public String getItemName() { return itemName; }
    public Long getItemId() { return itemId; }
    public String getBorrowerName() { return borrowerName; }
    public LocalDateTime getBorrowedAt() { return borrowedAt; }
    public LocalDateTime getReturnedAt() { return returnedAt; }
    public String getRemarks() { return remarks; }
}
