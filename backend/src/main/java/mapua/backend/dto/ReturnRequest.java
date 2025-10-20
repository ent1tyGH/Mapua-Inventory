package mapua.backend.dto;
import mapua.backend.entity.ConditionStatus;

public class ReturnRequest {
    private String serialNumber; // borrower serial
    private String remarks;
    private ConditionStatus conditionStatus;

    // getters and setters
    public String getSerialNumber() {
        return serialNumber;
    }
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public ConditionStatus getConditionStatus() { return conditionStatus; }
    public void setConditionStatus(ConditionStatus conditionStatus) { this.conditionStatus = conditionStatus; }
}