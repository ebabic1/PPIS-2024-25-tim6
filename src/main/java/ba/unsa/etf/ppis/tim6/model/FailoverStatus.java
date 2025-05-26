package ba.unsa.etf.ppis.tim6.model;

import jakarta.persistence.*;
import lombok.Getter;

import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class FailoverStatus {

    @Id
    private Long id = 1L;

    private String activeServer;
    private String status;
}
