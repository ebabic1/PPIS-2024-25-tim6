package ba.unsa.etf.ppis.tim6.mapper;

import ba.unsa.etf.ppis.tim6.dto.DocumentationDTO;
import ba.unsa.etf.ppis.tim6.model.Documentation;
import ba.unsa.etf.ppis.tim6.model.User;
import ba.unsa.etf.ppis.tim6.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class DocumentationMapper {

    @Autowired
    protected UserRepository userRepository;

    @Mapping(source = "createdBy", target = "createdBy")
    public abstract DocumentationDTO documentationToDocumentationDTO(Documentation documentation);

    @Mapping(source = "createdBy", target = "createdBy")
    public abstract Documentation documentationDTOToDocumentation(DocumentationDTO documentationDTO);

    protected Long userToUserId(User user) {
        return user != null ? user.getUserId() : null;
    }

    protected User userIdToUser(Long userId) {
        if (userId == null) return null;
        return userRepository.findById(userId).orElse(null);
    }
}