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
public class DocumentationDTO {
    private Long document_id;
    private String document_type;  // Representing the Enum as String for easier transfer
    private String content;
    private LocalDateTime created_at;
    private Long created_by;
    private String created_by_name;
}
