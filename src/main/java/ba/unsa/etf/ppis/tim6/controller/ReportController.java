package ba.unsa.etf.ppis.tim6.controller;

import ba.unsa.etf.ppis.tim6.dto.ReportDTO;
import ba.unsa.etf.ppis.tim6.mapper.ReportMapper;
import ba.unsa.etf.ppis.tim6.model.Report;
import ba.unsa.etf.ppis.tim6.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    // Create a new Report
    @PostMapping
    public ResponseEntity<ReportDTO> createReport(@RequestBody ReportDTO reportDTO) {
        Report report = reportMapper.reportDTOToReport(reportDTO);
        Report savedReport = reportRepository.save(report);
        return ResponseEntity.ok(reportMapper.reportToReportDTO(savedReport));
    }

    // Retrieve all Reports
    @GetMapping
    public ResponseEntity<List<ReportDTO>> getAllReports() {
        List<ReportDTO> reports = reportRepository.findAll().stream()
                .map(reportMapper::reportToReportDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reports);
    }

    // Retrieve a single Report by ID
    @GetMapping("/{id}")
    public ResponseEntity<ReportDTO> getReportById(@PathVariable Long id) {
        Optional<Report> report = reportRepository.findById(id);
        return report.map(value -> ResponseEntity.ok(reportMapper.reportToReportDTO(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Update an existing Report by ID
    @PutMapping("/{id}")
    public ResponseEntity<ReportDTO> updateReport(@PathVariable Long id, @RequestBody ReportDTO reportDTO) {
        return reportRepository.findById(id).map(existingReport -> {
            existingReport.setContent(reportDTO.getContent());
            // Add more fields as needed
            Report updated = reportRepository.save(existingReport);
            return ResponseEntity.ok(reportMapper.reportToReportDTO(updated));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delete a Report by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        if (reportRepository.existsById(id)) {
            reportRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
