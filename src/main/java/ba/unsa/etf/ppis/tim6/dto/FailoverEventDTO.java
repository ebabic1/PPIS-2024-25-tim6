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
public class FailoverEventDTO {
    public LocalDateTime timestamp;
    public String description;
}
