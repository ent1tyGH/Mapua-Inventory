package mapua.backend.controller;

import mapua.backend.service.CsvExportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/backup")
public class BackupController {

    private final CsvExportService csvExportService;

    public BackupController(CsvExportService csvExportService) {
        this.csvExportService = csvExportService;
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadBackup() throws IOException {
        Path tempDir = Files.createTempDirectory("backup_");
        csvExportService.exportAllToCsvFiles(tempDir);

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm"));
        Path zipPath = tempDir.resolve("database_backup_" + timestamp + ".zip");
        zipDirectory(tempDir, zipPath);

        byte[] zipBytes = Files.readAllBytes(zipPath);
        deleteDirectory(tempDir.toFile());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=database_backup_" + timestamp + ".zip")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(zipBytes);
    }

    private void zipDirectory(Path sourceDir, Path zipPath) throws IOException {
        try (ZipOutputStream zipOut = new ZipOutputStream(Files.newOutputStream(zipPath))) {
            Files.walk(sourceDir)
                    .filter(p -> !Files.isDirectory(p) && !p.equals(zipPath))
                    .forEach(p -> {
                        try {
                            zipOut.putNextEntry(new ZipEntry(sourceDir.relativize(p).toString()));
                            Files.copy(p, zipOut);
                            zipOut.closeEntry();
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
        }
    }

    private void deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) deleteDirectory(file);
        }
        dir.delete();
    }
}
