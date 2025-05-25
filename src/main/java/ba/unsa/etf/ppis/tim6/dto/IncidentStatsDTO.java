package ba.unsa.etf.ppis.tim6.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IncidentStatsDTO {
    private int total;
    private int open;
    private int closed;
}
