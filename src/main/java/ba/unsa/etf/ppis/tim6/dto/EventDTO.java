package ba.unsa.etf.ppis.tim6.dto;

import ba.unsa.etf.ppis.tim6.model.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Long event_id;
    private Event.EventType event_type;  // Representing the EventType enum as String for easy handling
    private String description;
    private LocalDateTime event_time;
    private Event.SeverityLevel severity_level;  // Representing SeverityLevel enum as String
    private Event.Status status;  // Representing Status enum as String

}
