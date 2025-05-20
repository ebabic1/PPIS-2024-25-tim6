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
public class IncidentDTO {
    private Long incident_id;
    private String title;
    private String description;
    private String priority;
    private String status;
    private LocalDateTime date_reported;
    private LocalDateTime date_resolved;
    private Long reported_by;
    private Long assigned_to;
    private String assigned_to_name;
    private String reported_by_name;
    private Long eventId;
    private String action_taken;
}
