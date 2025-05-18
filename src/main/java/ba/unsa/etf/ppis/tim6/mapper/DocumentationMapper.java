package ba.unsa.etf.ppis.tim6.mapper;

import ba.unsa.etf.ppis.tim6.dto.DocumentationDTO;
import ba.unsa.etf.ppis.tim6.model.Documentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class DocumentationMapper {

    @Mapping(target = "document_id", source = "documentId")
    @Mapping(target = "document_type", source = "documentType")
    @Mapping(target = "created_at", source = "createdAt", qualifiedByName = "formatDateTime")
    @Mapping(target = "created_by", source = "createdBy.userId")
    @Mapping(target = "created_by_name", source = "createdBy.username")
    public abstract DocumentationDTO documentationToDocumentationDTO(Documentation documentation);

    @Named("formatDateTime")
    String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

//    @Mapping(source = "created_by", target = "createdBy")
//    public abstract Documentation documentationDTOToDocumentation(DocumentationDTO documentationDTO);
}