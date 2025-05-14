package ba.unsa.etf.ppis.tim6.mapper;

import ba.unsa.etf.ppis.tim6.dto.IncidentDTO;
import ba.unsa.etf.ppis.tim6.model.Incident;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IncidentMapper {

    IncidentDTO incidentToIncidentDTO(Incident incident);

    Incident incidentDTOToIncident(IncidentDTO IncidentDTO);
}