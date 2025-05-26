package ba.unsa.etf.ppis.tim6.controller;

import ba.unsa.etf.ppis.tim6.dto.FailoverEventDTO;
import ba.unsa.etf.ppis.tim6.dto.FailoverStatusDTO;
import ba.unsa.etf.ppis.tim6.mapper.FailoverMapper;
import ba.unsa.etf.ppis.tim6.model.FailoverEvent;
import ba.unsa.etf.ppis.tim6.model.FailoverStatus;
import ba.unsa.etf.ppis.tim6.repository.FailoverEventRepository;
import ba.unsa.etf.ppis.tim6.repository.FailoverStatusRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/failover")
public class FailoverController {

    private final FailoverMapper failoverMapper;
    private final FailoverEventRepository failoverEventRepository;
    private final FailoverStatusRepository failoverStatusRepository;

    private String activeServer = "glavni";
    private String serverStatus = "ONLINE";
    private final List<FailoverEvent> events = new ArrayList<>();

    public FailoverController(FailoverMapper failoverMapper,
                              FailoverEventRepository failoverEventRepository,
                              FailoverStatusRepository failoverStatusRepository) {
        this.failoverMapper = failoverMapper;
        this.failoverEventRepository = failoverEventRepository;
        this.failoverStatusRepository = failoverStatusRepository;
    }

    @GetMapping("/status")
    public ResponseEntity<FailoverStatusDTO> getStatus() {
        FailoverStatus status = new FailoverStatus();
        status.setActiveServer(activeServer);
        status.setStatus(serverStatus);

        failoverStatusRepository.save(status);

        FailoverStatusDTO dto = failoverMapper.failoverStatusToFailoverStatusDTO(status);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/switch-to-backup")
    public ResponseEntity<Void> switchToBackup() {
        if ("rezervni".equals(activeServer)) {
            return ResponseEntity.badRequest().build();
        }
        activeServer = "rezervni";
        serverStatus = "ONLINE";
        addEvent("Switched to backup server");
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/switch-to-main")
    public ResponseEntity<Void> switchToMain() {
        if ("glavni".equals(activeServer)) {
            return ResponseEntity.badRequest().build();
        }
        activeServer = "glavni";
        serverStatus = "ONLINE";
        addEvent("Switched back to main server");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/events")
    public ResponseEntity<List<FailoverEventDTO>> getEvents() {
        List<FailoverEvent> events = failoverEventRepository.findAllByOrderByTimestampDesc();
        List<FailoverEventDTO> dtos = events.stream()
                .map(failoverMapper::failoverEventToFailoverEventDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/simulate-failure")
    public ResponseEntity<Void> simulateFailure() {
        if (!"glavni".equals(activeServer)) {
            return ResponseEntity.badRequest().build();
        }
        serverStatus = "OFFLINE";
        addEvent("Simulated failure of the main server");
        return ResponseEntity.noContent().build();
    }

    private void addEvent(String description) {
        FailoverEvent event = new FailoverEvent();
        event.setEventDescription(description);
        event.setTimestamp(LocalDateTime.now());

        failoverEventRepository.save(event);
    }
}
