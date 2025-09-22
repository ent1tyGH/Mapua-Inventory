package mapua.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "equipment_type_id", nullable = false)
    private EquipmentType equipmentType;

    private String specifications;
    private String location;

    @Enumerated(EnumType.STRING)
    private ConditionStatus conditionStatus;

    private boolean borrowed;  // track if currently borrowed

    // history of borrowing transactions
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<BorrowRecord> borrowHistory = new ArrayList<>();


    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public EquipmentType getEquipmentType() { return equipmentType; }
    public void setEquipmentType(EquipmentType equipmentType) { this.equipmentType = equipmentType; }

    public String getSpecifications() { return specifications; }
    public void setSpecifications(String specifications) { this.specifications = specifications; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public ConditionStatus getConditionStatus() { return conditionStatus; }
    public void setConditionStatus(ConditionStatus conditionStatus) { this.conditionStatus = conditionStatus; }

    public boolean isBorrowed() { return borrowed; }
    public void setBorrowed(boolean borrowed) { this.borrowed = borrowed; }

    public List<BorrowRecord> getBorrowHistory() { return borrowHistory; }
    public void setBorrowHistory(List<BorrowRecord> borrowHistory) { this.borrowHistory = borrowHistory; }
}
