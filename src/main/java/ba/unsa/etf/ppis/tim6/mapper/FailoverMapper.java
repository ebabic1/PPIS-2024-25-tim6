package ba.unsa.etf.ppis.tim6.mapper;

import ba.unsa.etf.ppis.tim6.dto.FailoverEventDTO;
import ba.unsa.etf.ppis.tim6.dto.FailoverStatusDTO;
import ba.unsa.etf.ppis.tim6.model.FailoverEvent;
import ba.unsa.etf.ppis.tim6.model.FailoverStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FailoverMapper {

    FailoverStatusDTO failoverStatusToFailoverStatusDTO(FailoverStatus status);

    FailoverStatus failoverStatusDTOToFailoverStatus(FailoverStatusDTO dto);

    @Mapping(source = "eventDescription", target = "description")
    FailoverEventDTO failoverEventToFailoverEventDTO(FailoverEvent event);

    @Mapping(source = "description", target = "eventDescription")
    FailoverEvent failoverEventDTOToFailoverEvent(FailoverEventDTO dto);

    List<FailoverEventDTO> failoverEventsToFailoverEventDTOs(List<FailoverEvent> events);
}
