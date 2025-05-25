package ba.unsa.etf.ppis.tim6.controller;

import ba.unsa.etf.ppis.tim6.dto.SensorDataDTO;
import ba.unsa.etf.ppis.tim6.mapper.IncidentMapper;
import ba.unsa.etf.ppis.tim6.mapper.SensorDataMapper;
import ba.unsa.etf.ppis.tim6.model.SensorData;
import ba.unsa.etf.ppis.tim6.repository.SensorDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sensor-data")
@RequiredArgsConstructor
public class SensorDataController {

    private final SensorDataRepository sensorDataRepository;
    private final SensorDataMapper sensorDataMapper;

    @PostMapping
    public ResponseEntity<SensorDataDTO> createSensorData(@RequestBody SensorDataDTO sensorDataDTO) {
        SensorData sensorData = new SensorData();
        sensorData.setTimestamp(sensorDataDTO.getTimestamp());
        sensorData.setTemperature(sensorDataDTO.getTemperature());

        SensorData savedSensorData = sensorDataRepository.save(sensorData);
        return ResponseEntity.ok(sensorDataMapper.sensorDataToSensorDataDTO(savedSensorData));
    }

    @GetMapping
    public ResponseEntity<List<SensorDataDTO>> getAllSensorData() {
        List<SensorDataDTO> sensorDataList = sensorDataRepository.findAll().stream()
                .map(sensorDataMapper::sensorDataToSensorDataDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sensorDataList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorDataDTO> getSensorDataById(@PathVariable Long id) {
        Optional<SensorData> sensorData = sensorDataRepository.findById(id);
        return sensorData.map(data -> ResponseEntity.ok(sensorDataMapper.sensorDataToSensorDataDTO(data)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SensorDataDTO> updateSensorData(@PathVariable Long id, @RequestBody SensorDataDTO sensorDataDTO) {
        return sensorDataRepository.findById(id).map(existingData -> {
            existingData.setTemperature(sensorDataDTO.getTemperature());
            existingData.setTimestamp(sensorDataDTO.getTimestamp());

            SensorData updatedSensorData = sensorDataRepository.save(existingData);
            return ResponseEntity.ok(sensorDataMapper.sensorDataToSensorDataDTO(updatedSensorData));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSensorData(@PathVariable Long id) {
        if (sensorDataRepository.existsById(id)) {
            sensorDataRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
