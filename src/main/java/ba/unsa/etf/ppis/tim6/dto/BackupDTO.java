package ba.unsa.etf.ppis.tim6.dto;

import ba.unsa.etf.ppis.tim6.model.Backup;
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
    private LocalDateTime backupTime;
    private Integer backupSize;
    private String backupLocation;
    private Backup.Status status;  // Representing the Enum as String for easier transfer
}
