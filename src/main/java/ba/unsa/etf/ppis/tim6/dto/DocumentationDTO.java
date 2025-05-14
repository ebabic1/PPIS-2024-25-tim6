package ba.unsa.etf.ppis.tim6.dto;

import ba.unsa.etf.ppis.tim6.model.Documentation;
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
    private Documentation.DocumentType documentType;  // Representing the Enum as String for easier transfer
    private String content;
    private LocalDateTime createdAt;
    private Long createdBy;  // Representing the user as the user ID
}
