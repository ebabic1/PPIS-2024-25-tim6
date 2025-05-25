package ba.unsa.etf.ppis.tim6.model;

import jakarta.persistence.*;
import lombok.Getter;

import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private double xCoordinate;
    private double yCoordinate;
    @OneToMany(mappedBy = "device")
    private List<SensorData> sensorDataList;
}
