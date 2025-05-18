package ba.unsa.etf.ppis.tim6.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateIncidentDTO {
    private String title;
    private String description;
    private String priority;  // Stored as a String for easier transfer (could be Enum name)
    private String status;    // Stored as a String for easier transfer (could be Enum name)
    private LocalDateTime date_reported;
    private LocalDateTime date_resolved;
    private Long reported_by; // Instead of the full user, you could just pass the user ID
    private Long assigned_to; // Instead of the full user, you could just pass the user ID
    private String assigned_to_name;
    private String reported_by_name;
    private Long eventId;
}
