package ba.unsa.etf.ppis.tim6.dto;

import ba.unsa.etf.ppis.tim6.model.Event;
import ba.unsa.etf.ppis.tim6.model.Incident;
import ba.unsa.etf.ppis.tim6.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncidentDTO {
    private String title;
    private String description;
    private Incident.Priority priority;  // Stored as a String for easier transfer (could be Enum name)
    private Incident.Status status;    // Stored as a String for easier transfer (could be Enum name)
    private LocalDateTime dateReported;
    private LocalDateTime dateResolved;
    private Long reportedById; // Instead of the full user, you could just pass the user ID
    private Long assignedToId; // Instead of the full user, you could just pass the user ID
    private Long eventId;      // Instead of the full event, you could just pass the event ID

}
