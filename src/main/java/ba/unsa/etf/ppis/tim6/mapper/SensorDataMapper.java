package ba.unsa.etf.ppis.tim6.mapper;

import ba.unsa.etf.ppis.tim6.dto.SensorDataDTO;
import ba.unsa.etf.ppis.tim6.model.SensorData;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SensorDataMapper {
    SensorDataDTO sensorDataToSensorDataDTO(SensorData sensorData);

    SensorData sensorDataDTOToSensorData(SensorDataDTO sensorDataDTO);
}
