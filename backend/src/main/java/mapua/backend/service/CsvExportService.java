package mapua.backend.service;

import mapua.backend.entity.*;
import mapua.backend.repository.*;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CsvExportService {

    private final EquipmentTypeRepository equipmentTypeRepository;
    private final ItemRepository itemRepository;
    private final BorrowerRepository borrowerRepository;
    private final BorrowRecordRepository borrowRecordRepository;
    private final DailyReportRepository dailyReportRepository;

    public CsvExportService(EquipmentTypeRepository equipmentTypeRepository,
                            ItemRepository itemRepository,
                            BorrowerRepository borrowerRepository,
                            BorrowRecordRepository borrowRecordRepository,
                            DailyReportRepository dailyReportRepository) {
        this.equipmentTypeRepository = equipmentTypeRepository;
        this.itemRepository = itemRepository;
        this.borrowerRepository = borrowerRepository;
        this.borrowRecordRepository = borrowRecordRepository;
        this.dailyReportRepository = dailyReportRepository;
    }

    public Path exportAllToCsvFiles(Path outputDir) throws IOException {
        if (!Files.exists(outputDir)) Files.createDirectories(outputDir);

        exportEquipmentTypes(outputDir.resolve("equipment_type.csv"));
        exportItems(outputDir.resolve("item.csv"));
        exportBorrowers(outputDir.resolve("borrowers.csv"));
        exportBorrowRecords(outputDir.resolve("borrow_records.csv"));
        exportDailyReports(outputDir.resolve("daily_reports.csv"));

        return outputDir;
    }

    private void exportEquipmentTypes(Path file) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            writer.write("id,name\n");
            for (EquipmentType e : equipmentTypeRepository.findAll()) {
                writer.write(e.getId() + ",\"" + safe(e.getName()) + "\"\n");
            }
        }
    }

    private void exportItems(Path file) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            writer.write("id,equipment_type_id,specifications,location,condition_status,borrowed\n");
            for (Item i : itemRepository.findAll()) {
                writer.write(i.getId() + ","
                        + (i.getEquipmentType() != null ? i.getEquipmentType().getId() : "") + ","
                        + "\"" + safe(i.getSpecifications()) + "\","
                        + "\"" + safe(i.getLocation()) + "\","
                        + "\"" + safe(i.getConditionStatus() != null ? i.getConditionStatus().toString() : "") + "\","
                        + i.isBorrowed() + "\n");
            }
        }
    }

    private void exportBorrowers(Path file) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            writer.write("id,serial_number,student_name,student_number\n");
            for (Borrower b : borrowerRepository.findAll()) {
                writer.write(b.getId() + ","
                        + "\"" + safe(b.getSerialNumber()) + "\","
                        + "\"" + safe(b.getStudentName()) + "\","
                        + "\"" + safe(b.getStudentNumber()) + "\"\n");
            }
        }
    }

    private void exportBorrowRecords(Path file) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            writer.write("id,borrower_id,item_id,borrowed_at,returned_at,remarks\n");
            for (BorrowRecord r : borrowRecordRepository.findAll()) {
                writer.write(r.getId() + ","
                        + (r.getBorrower() != null ? r.getBorrower().getId() : "") + ","
                        + (r.getItem() != null ? r.getItem().getId() : "") + ","
                        + "\"" + safe(String.valueOf(r.getBorrowedAt())) + "\","
                        + "\"" + safe(String.valueOf(r.getReturnedAt())) + "\","
                        + "\"" + safe(r.getRemarks()) + "\"\n");
            }
        }
    }

    private void exportDailyReports(Path file) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            writer.write("id,created_at,report_text\n");
            for (DailyReport d : dailyReportRepository.findAll()) {
                writer.write(d.getId() + ","
                        + "\"" + safe(String.valueOf(d.getCreatedAt())) + "\","
                        + "\"" + safe(d.getReportText()) + "\"\n");
            }
        }
    }

    private String safe(String val) {
        return val == null ? "" : val.replace("\"", "\"\"");
    }
}
