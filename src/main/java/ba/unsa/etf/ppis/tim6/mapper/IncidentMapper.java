package ba.unsa.etf.ppis.tim6.mapper;

import ba.unsa.etf.ppis.tim6.dto.CreateIncidentDTO;
import ba.unsa.etf.ppis.tim6.dto.IncidentDTO;
import ba.unsa.etf.ppis.tim6.dto.UpdateIncidentDTO;
import ba.unsa.etf.ppis.tim6.model.Incident;
import ba.unsa.etf.ppis.tim6.model.User;
import ba.unsa.etf.ppis.tim6.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.sql.Update;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class IncidentMapper {

    @Autowired
    protected UserRepository userRepository;

    @Mapping(target = "incident_id", source = "incidentId")
    @Mapping(target = "date_reported", source = "dateReported", qualifiedByName = "formatDateTime")
    @Mapping(target = "date_resolved", source = "dateResolved", qualifiedByName = "formatDateTime")
    @Mapping(target = "reported_by", source = "reportedBy.userId")
    @Mapping(target = "assigned_to", source = "assignedTo.userId")
    @Mapping(target = "reported_by_name", source = "reportedBy.username")
    @Mapping(target = "assigned_to_name", source = "assignedTo.username")
    @Mapping(target = "action_taken", source = "actionTaken")
    public abstract IncidentDTO incidentToIncidentDTO(Incident incident);

    @Mapping(target = "dateReported", source = "date_reported", qualifiedByName = "parseDateTime")
    @Mapping(target = "dateResolved", source = "date_resolved", qualifiedByName = "parseDateTime")
    @Mapping(target = "reportedBy", source = "reported_by", qualifiedByName = "longToUser")
    @Mapping(target = "assignedTo", source = "assigned_to", qualifiedByName = "longToUser")
    public abstract Incident createIncidentDTOToIncident(CreateIncidentDTO createIncidentDTO);

    @Named("longToUser")
    public User longToUser(Long userId) {
        if (userId == null) {
            return null;
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }

    @Named("formatDateTime")
    public String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @Named("parseDateTime")
    public LocalDateTime parseDateTime(String dateString) {
        if (dateString == null) return null;
        return LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME);
    }
}