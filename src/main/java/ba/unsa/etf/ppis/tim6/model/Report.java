package ba.unsa.etf.ppis.tim6.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @Lob
    private String content;

    private LocalDateTime createdAt;

    private boolean includeIncidents;

    private boolean includeEvents;

    private boolean includeArticleStats;

    private boolean includeOrders;

    private boolean includeDeviceStats;

    private boolean includeBackups;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    public enum ReportType {
        MONTHLY,
        QUARTERLY,
        YEARLY
    }
}
