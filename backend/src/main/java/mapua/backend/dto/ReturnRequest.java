package mapua.backend.dto;

public class ReturnRequest {
    private String serialNumber; // borrower serial
    private String remarks;

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
}