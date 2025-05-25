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
public class GenerateReportDTO {
    private Report.ReportType reportType;
    private String content;
    private LocalDateTime createdAt;
    private String createdByUsername;

    private boolean includeIncidents;
    private boolean includeEvents;
    private boolean includeArticleStats;
    private boolean includeOrders;
    private boolean includeDeviceStats;
    private boolean includeBackups;
}

