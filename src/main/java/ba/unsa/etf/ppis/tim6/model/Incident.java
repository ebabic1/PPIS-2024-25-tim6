package ba.unsa.etf.ppis.tim6.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long incidentId;

    private String title;

    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime dateReported;
    private LocalDateTime dateResolved;

    @ManyToOne
    @JoinColumn(name = "reported_by")
    private User reportedBy;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    public String actionTaken;

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }

    public enum Status {
        OPEN,
        CLOSED,
        ESCALATED
    }
}
