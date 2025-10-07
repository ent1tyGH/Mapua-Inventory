package mapua.backend.service;

import mapua.backend.entity.BorrowRecord;
import mapua.backend.entity.Item;
import mapua.backend.entity.DailyReport;
import mapua.backend.repository.BorrowRecordRepository;
import mapua.backend.repository.ItemRepository;
import mapua.backend.repository.DailyReportRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportService {

    private final BorrowRecordRepository borrowRecordRepository;
    private final ItemRepository itemRepository;
    private final DailyReportRepository dailyReportRepository;

    public ExcelExportService(BorrowRecordRepository borrowRecordRepository,
                              ItemRepository itemRepository,
                              DailyReportRepository dailyReportRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.itemRepository = itemRepository;
        this.dailyReportRepository = dailyReportRepository;
    }

    public byte[] exportDatabaseToExcel() throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {

            // --- Sheet 1: Borrow Records ---
            Sheet borrowSheet = workbook.createSheet("Borrow Records");
            createBorrowRecordSheet(borrowSheet);

            // --- Sheet 2: Items ---
            Sheet itemSheet = workbook.createSheet("Items");
            createItemSheet(itemSheet);

            // --- Sheet 3: Daily Reports ---
            Sheet reportSheet = workbook.createSheet("Daily Reports");
            createDailyReportSheet(reportSheet);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();
        }
    }

    private void createBorrowRecordSheet(Sheet sheet) {
        Row header = sheet.createRow(0);
        String[] columns = {"ID", "Borrower", "Item", "Borrowed At", "Returned At", "Remarks"};
        for (int i = 0; i < columns.length; i++) {
            header.createCell(i).setCellValue(columns[i]);
        }

        List<BorrowRecord> records = borrowRecordRepository.findAll();
        int rowIdx = 1;
        for (BorrowRecord record : records) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(record.getId());
            row.createCell(1).setCellValue(record.getBorrower() != null ? record.getBorrower().getStudentName() : "");
            row.createCell(2).setCellValue(record.getItem() != null && record.getItem().getEquipmentType() != null
                    ? record.getItem().getEquipmentType().getName()
                    : "");
            row.createCell(3).setCellValue(record.getBorrowedAt() != null ? record.getBorrowedAt().toString() : "");
            row.createCell(4).setCellValue(record.getReturnedAt() != null ? record.getReturnedAt().toString() : "");
            row.createCell(5).setCellValue(record.getRemarks() != null ? record.getRemarks() : "");
        }
    }

    private void createItemSheet(Sheet sheet) {
        Row header = sheet.createRow(0);
        String[] columns = {"ID", "Equipment Type", "Specifications", "Is Borrowed"};
        for (int i = 0; i < columns.length; i++) {
            header.createCell(i).setCellValue(columns[i]);
        }

        List<Item> items = itemRepository.findAll();
        int rowIdx = 1;
        for (Item item : items) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(item.getId());
            row.createCell(1).setCellValue(item.getEquipmentType() != null ? item.getEquipmentType().getName() : "");
            row.createCell(2).setCellValue(item.getSpecifications() != null ? item.getSpecifications() : "");
            row.createCell(3).setCellValue(item.isBorrowed());
        }
    }

    private void createDailyReportSheet(Sheet sheet) {
        Row header = sheet.createRow(0);
        String[] columns = {"ID", "Created At", "Report Text"};
        for (int i = 0; i < columns.length; i++) {
            header.createCell(i).setCellValue(columns[i]);
        }

        List<DailyReport> reports = dailyReportRepository.findAll();
        int rowIdx = 1;
        for (DailyReport report : reports) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(report.getId());
            row.createCell(1).setCellValue(
                    report.getCreatedAt() != null ? report.getCreatedAt().toString() : ""
            );
            row.createCell(2).setCellValue(
                    report.getReportText() != null ? report.getReportText() : ""
            );
        }
    }
}
