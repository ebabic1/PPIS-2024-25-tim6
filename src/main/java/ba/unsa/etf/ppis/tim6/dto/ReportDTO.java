package ba.unsa.etf.ppis.tim6.dto;

import ba.unsa.etf.ppis.tim6.model.Report;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    private Report.ReportType reportType;  // Representing the ReportType enum as String for easy handling
    private String content;
    private LocalDateTime createdAt;
    private Long createdById;
}
