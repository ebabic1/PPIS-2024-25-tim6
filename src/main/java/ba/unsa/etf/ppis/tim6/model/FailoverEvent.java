package ba.unsa.etf.ppis.tim6.model;

import jakarta.persistence.*;
import lombok.Getter;

import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class FailoverEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;
    private String eventDescription;
}
