package ba.unsa.etf.ppis.tim6.controller;

import ba.unsa.etf.ppis.tim6.dto.ReportDTO;
import ba.unsa.etf.ppis.tim6.mapper.ReportMapper;
import ba.unsa.etf.ppis.tim6.model.Report;
import ba.unsa.etf.ppis.tim6.repository.ReportRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

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

    @GetMapping("/generate-pdf/{id}")
    public ResponseEntity<byte[]> generatePdfReportById(@PathVariable Long id) {
        Optional<Report> reportOpt = reportRepository.findById(id);
        if (reportOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Report report = reportOpt.get();

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Report ID: " + report.getReportId())
                    .setFontSize(16));

            document.add(new Paragraph("Report Type: " + report.getReportType()));

            document.add(new Paragraph("Created At: " + report.getCreatedAt()));

            document.add(new Paragraph("Created By: " + report.getCreatedBy().getUsername()));

            document.add(new Paragraph("Content:"));
            document.add(new Paragraph(report.getContent()));

            document.close();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "report_" + id + ".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(baos.toByteArray());

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
