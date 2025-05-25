package ba.unsa.etf.ppis.tim6.controller;

import ba.unsa.etf.ppis.tim6.dto.DeviceDTO;
import ba.unsa.etf.ppis.tim6.dto.SensorDataDTO;
import ba.unsa.etf.ppis.tim6.mapper.DeviceMapper;
import ba.unsa.etf.ppis.tim6.mapper.SensorDataMapper;
import ba.unsa.etf.ppis.tim6.model.Device;
import ba.unsa.etf.ppis.tim6.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;
    private final SensorDataMapper sensorDataMapper;

    @GetMapping
    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
        List<Device> devices = deviceRepository.findAll();
        List<DeviceDTO> deviceDTOs = devices.stream()
                .map(deviceMapper::deviceToDeviceDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(deviceDTOs);
    }
}
