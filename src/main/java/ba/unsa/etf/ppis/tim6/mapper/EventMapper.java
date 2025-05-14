package ba.unsa.etf.ppis.tim6.mapper;

import ba.unsa.etf.ppis.tim6.dto.EventDTO;
import ba.unsa.etf.ppis.tim6.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    EventDTO eventToEventDTO(Event Event);

    Event eventDTOToEvent(EventDTO EventDTO);
}