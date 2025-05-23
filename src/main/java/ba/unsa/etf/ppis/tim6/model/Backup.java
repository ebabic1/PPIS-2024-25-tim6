package ba.unsa.etf.ppis.tim6.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Backup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long backupId;

    private LocalDateTime backupTime;

    private String backupSize;

    private String backupLocation;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        SUCCESSFUL,
        FAILED,
        PENDING
    }
}
