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
public class BackupDTO {
    private Long backup_id;
    private LocalDateTime backup_time;
    private String backup_size;
    private String backup_location;
    private String status;
}
