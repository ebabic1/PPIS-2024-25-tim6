package ba.unsa.etf.ppis.tim6.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FailoverStatusDTO {
    public String activeServer;
    public String status = "ONLINE";
}
