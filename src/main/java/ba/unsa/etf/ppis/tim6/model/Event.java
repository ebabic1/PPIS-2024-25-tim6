package ba.unsa.etf.ppis.tim6.model;

import jakarta.persistence.*;
import lombok.Getter;

import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Lob
    private String description;

    private LocalDateTime eventTime;

    @Enumerated(EnumType.STRING)
    private SeverityLevel severityLevel;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum EventType {
        FLOOD,
        FIRE,
        APPLICATION_ERROR
    }

    public enum SeverityLevel {
        CRITICAL,
        HIGH,
        MEDIUM,
        LOW
    }

    public enum Status {
        ACTIVE,
        COMPLETED
    }
}
