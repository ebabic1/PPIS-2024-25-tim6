package ba.unsa.etf.ppis.tim6.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class SensorData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double dischargePressure;
    private double fanMotorCurrent;
    private double airflowRate;
    private double vibrationLevel;
    private double temperature;
    private boolean energySaver;
    private double voltageOutput;
    private double currentLimit;
    private double portSpeed;
    private boolean accessLocked;
    private LocalDateTime timestamp;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;
}
