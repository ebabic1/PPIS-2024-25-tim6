package ba.unsa.etf.ppis.tim6.controller;

import ba.unsa.etf.ppis.tim6.dto.GenerateReportDTO;
import ba.unsa.etf.ppis.tim6.dto.ReportDTO;
import ba.unsa.etf.ppis.tim6.mapper.ReportMapper;
import ba.unsa.etf.ppis.tim6.model.*;
import ba.unsa.etf.ppis.tim6.repository.*;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportRepository reportRepository;
    private final IncidentRepository incidentRepository;
    private final EventRepository eventRepository;
    private final BackupRepository backupRepository;
    private final ReportMapper reportMapper;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<ReportDTO> createReport(@RequestBody ReportDTO reportDTO) {
        Report report = reportMapper.reportDTOToReport(reportDTO);
        Report savedReport = reportRepository.save(report);
        return ResponseEntity.ok(reportMapper.reportToReportDTO(savedReport));
    }

    @GetMapping
    public ResponseEntity<List<ReportDTO>> getAllReports() {
        List<ReportDTO> reports = reportRepository.findAll().stream()
                .map(reportMapper::reportToReportDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportDTO> getReportById(@PathVariable Long id) {
        Optional<Report> report = reportRepository.findById(id);
        return report.map(value -> ResponseEntity.ok(reportMapper.reportToReportDTO(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/generate-report")
    public ResponseEntity<ReportDTO> generateReport(@RequestBody GenerateReportDTO dto) {
        Report report = reportMapper.generateReportDTOToReport(dto);
        report.setCreatedAt(LocalDateTime.now());

        User creator = userRepository.findByUsername(dto.getCreatedByUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        report.setCreatedBy(creator);

        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append(dto.getReportType().name().charAt(0))
                .append(dto.getReportType().name().substring(1).toLowerCase())
                .append(" report covering ");

        List<String> includedSections = new ArrayList<>();
        if (dto.isIncludeIncidents()) includedSections.add("incidents");
        if (dto.isIncludeEvents()) includedSections.add("events");
        if (dto.isIncludeArticleStats()) includedSections.add("article statistics");
        if (dto.isIncludeOrders()) includedSections.add("orders");
        if (dto.isIncludeDeviceStats()) includedSections.add("device statistics");
        if (dto.isIncludeBackups()) includedSections.add("backups");

        if (includedSections.isEmpty()) {
            contentBuilder.append("no filters.");
        } else {
            contentBuilder.append(String.join(", ", includedSections)).append(".");
        }

        report.setContent(contentBuilder.toString());

        Report saved = reportRepository.save(report);
        return ResponseEntity.ok(reportMapper.reportToReportDTO(saved));
    }

    @GetMapping("/generate-pdf/{id}")
    public ResponseEntity<byte[]> generatePdfReportById(@PathVariable Long id) {
        Optional<Report> reportOpt = reportRepository.findById(id);
        if (reportOpt.isEmpty()) return ResponseEntity.notFound().build();

        Report report = reportOpt.get();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document doc = new Document(pdf);

            doc.add(new Paragraph("Report ID: " + report.getReportId()).setFontSize(16));
            doc.add(new Paragraph("Report Type: " + report.getReportType()));
            doc.add(new Paragraph("Created At: " + report.getCreatedAt()));
            doc.add(new Paragraph("Created By: " + report.getCreatedBy().getUsername()));

            if (report.isIncludeIncidents()) {
                doc.add(new Paragraph("\n--- Incidents ---"));
                List<Incident> incidents = incidentRepository.findAll();
                for (Incident i : incidents) {
                    doc.add(new Paragraph("• " + i.getTitle() + " - " + i.getStatus()));
                }
            }

            if (report.isIncludeEvents()) {
                doc.add(new Paragraph("\n--- Events ---"));
                List<Event> events = eventRepository.findAll();
                for (Event e : events) {
                    doc.add(new Paragraph("• " + e.getEventType() + " - " + e.getSeverityLevel()));
                }
            }

            if (report.isIncludeBackups()) {
                doc.add(new Paragraph("\n--- Backups ---"));
                List<Backup> backups = backupRepository.findAll();
                for (Backup b : backups) {
                    doc.add(new Paragraph("• Time: " + b.getBackupTime() + ", Size: " + b.getBackupSize()));
                }
            }

            if (report.isIncludeArticleStats()) {
                doc.add(new Paragraph("\n--- Article Statistics ---"));
                doc.add(new Paragraph("• Total Items in Inventory: 520"));
                doc.add(new Paragraph("• Low Stock Items: 12"));
                doc.add(new Paragraph("• Most Requested Item: USB-C Adapter"));
                doc.add(new Paragraph("• Average Restock Time: 3 days"));
                doc.add(new Paragraph("• Inventory Sufficiency: 98% of item needs have been met"));
            }

            if (report.isIncludeOrders()) {
                doc.add(new Paragraph("\n--- Orders Statistics ---"));
                doc.add(new Paragraph("• Total Orders This Period: 74"));
                doc.add(new Paragraph("• Completed Orders: 69"));
                doc.add(new Paragraph("• Pending Orders: 5"));
                doc.add(new Paragraph("• Most Ordered Category: IT Equipment"));
                doc.add(new Paragraph("• Average Delivery Time: 2.7 days"));
                doc.add(new Paragraph("• Order Fulfillment Rate: 95%"));
            }

            if (report.isIncludeDeviceStats()) {
                doc.add(new Paragraph("\n--- Device Statistics ---"));
                doc.add(new Paragraph("• Active Devices: 18"));
                doc.add(new Paragraph("• Offline Devices: 2"));
                doc.add(new Paragraph("• Devices Needing Maintenance: 1"));
                doc.add(new Paragraph("• Most Used Device: Printer HP-LJ2055"));
                doc.add(new Paragraph("• Average Daily Usage Time: 6.4 hours"));
                doc.add(new Paragraph("• Device Availability: 96%"));
            }


            doc.close();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "report_" + id + ".pdf");

            return ResponseEntity.ok().headers(headers).body(baos.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<ReportDTO> updateReport(@PathVariable Long id, @RequestBody ReportDTO reportDTO) {
        return reportRepository.findById(id).map(existingReport -> {
            existingReport.setContent(reportDTO.getContent());
            // Add more fields as needed
            Report updated = reportRepository.save(existingReport);
            return ResponseEntity.ok(reportMapper.reportToReportDTO(updated));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        if (reportRepository.existsById(id)) {
            reportRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
