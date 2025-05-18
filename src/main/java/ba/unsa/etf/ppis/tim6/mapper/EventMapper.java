package ba.unsa.etf.ppis.tim6.mapper;

import ba.unsa.etf.ppis.tim6.dto.EventDTO;
import ba.unsa.etf.ppis.tim6.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    @Mapping(target = "event_id", source = "eventId")
    @Mapping(target = "event_type", source = "eventType")
    @Mapping(target = "event_time", source = "eventTime")
    @Mapping(target = "severity_level", source = "severityLevel")
    EventDTO eventToEventDTO(Event Event);

    Event eventDTOToEvent(EventDTO EventDTO);
}