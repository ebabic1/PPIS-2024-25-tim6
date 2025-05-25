package ba.unsa.etf.ppis.tim6.dto;

import ba.unsa.etf.ppis.tim6.model.SensorData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeviceDTO {

    private Long id;
    private String name;
    private String type;
    private double xCoordinate;
    private double yCoordinate;
    private List<SensorData> sensorDataList;
}
