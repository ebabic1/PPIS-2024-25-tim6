package ba.unsa.etf.ppis.tim6.mapper;

import ba.unsa.etf.ppis.tim6.dto.DeviceDTO;
import ba.unsa.etf.ppis.tim6.model.Device;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = SensorDataMapper.class)
public interface DeviceMapper {
    DeviceDTO deviceToDeviceDTO(Device device);
    Device deviceDTOToDevice(DeviceDTO deviceDTO);
}
