package ba.unsa.etf.ppis.tim6.controller;

import ba.unsa.etf.ppis.tim6.dto.*;
import ba.unsa.etf.ppis.tim6.mapper.BackupMapper;
import ba.unsa.etf.ppis.tim6.model.Backup;
import ba.unsa.etf.ppis.tim6.repository.BackupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/backups")
@RequiredArgsConstructor
public class BackupController {

    private final BackupRepository backupRepository;
    private final BackupMapper backupMapper;

    @PostMapping
    public ResponseEntity<BackupDTO> createBackup(@RequestBody CreateBackupDTO createBackupDTO) {
        Backup backup = backupMapper.createBackupDTOToBackup(createBackupDTO);
        Backup savedBackup = backupRepository.save(backup);
        return ResponseEntity.ok(backupMapper.backupToBackupDTO(savedBackup));
    }

    @GetMapping
    public ResponseEntity<List<BackupDTO>> getAllBackups() {
        List<Backup> backups = backupRepository.findAll();
        List<BackupDTO> backupDTOs = backups.stream()
                .map(backupMapper::backupToBackupDTO)
                .toList();
        return ResponseEntity.ok(backupDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BackupDTO> getBackupById(@PathVariable Long id) {
        Optional<Backup> backup = backupRepository.findById(id);
        return backup.map(b -> ResponseEntity.ok(backupMapper.backupToBackupDTO(b)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BackupDTO> updateBackup(@PathVariable Long id, @RequestBody BackupDTO backupDTO) {
        Optional<Backup> optionalBackup = backupRepository.findById(id);
        if (optionalBackup.isPresent()) {
            Backup backup = optionalBackup.get();
            backup.setBackupTime(backupDTO.getBackup_time());
            backup.setBackupLocation(backupDTO.getBackup_location());
            backup.setStatus(Backup.Status.valueOf(backupDTO.getStatus()));
            backup.setBackupSize(backupDTO.getBackup_size());
            Backup updatedBackup = backupRepository.save(backup);
            return ResponseEntity.ok(backupMapper.backupToBackupDTO(updatedBackup));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBackup(@PathVariable Long id) {
        if (backupRepository.existsById(id)) {
            backupRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/latest")
    public ResponseEntity<LatestBackupDTO> getLatestBackup() {
        Backup latestBackup = backupRepository.findTopByStatusOrderByBackupTimeDesc(Backup.Status.SUCCESSFUL)
                .orElseThrow(() -> new RuntimeException("No backup found"));

        LatestBackupDTO dto = new LatestBackupDTO(
                "Latest Backup",
                latestBackup.getStatus().toString(),
                formatBackupTime(latestBackup),
                latestBackup.getBackupSize(),
                latestBackup.getBackupLocation()
        );

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/next")
    public ResponseEntity<NextBackupDTO> getNextBackup() {
        LocalDateTime currentTime = LocalDateTime.now();
        Backup nextBackup = backupRepository.findFirstByBackupTimeGreaterThanOrderByBackupTimeAsc(currentTime)
                .orElseThrow(() -> new RuntimeException("No upcoming backups scheduled"));

        NextBackupDTO dto = new NextBackupDTO(
                "Next Backup",
                formatScheduledTime(nextBackup.getBackupTime()),
                calculateTimeUntil(currentTime, nextBackup.getBackupTime())
        );

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/failed")
    public ResponseEntity<FailedBackupDTO> getFailedBackups() {
        List<Backup> failedBackups = backupRepository.findByStatusOrderByBackupTimeDesc(Backup.Status.FAILED);

        int number = failedBackups.size();
        String lastFailTime = number > 0 ? formatScheduledTime(failedBackups.getFirst().getBackupTime()) : null;

        FailedBackupDTO dto = new FailedBackupDTO("Failed Backup", lastFailTime, number);
        return ResponseEntity.ok(dto);
    }

    private String formatScheduledTime(LocalDateTime scheduledTime) {
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.DAYS);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");

        if (scheduledTime.toLocalDate().equals(LocalDateTime.now().toLocalDate())) {
            return scheduledTime.format(timeFormatter) + " Today";
        } else if (scheduledTime.toLocalDate().equals(tomorrow.toLocalDate())) {
            return scheduledTime.format(timeFormatter) + " Tomorrow";
        } else {
            return scheduledTime.format(DateTimeFormatter.ofPattern("h:mm a MMM d"));
        }
    }

    private String calculateTimeUntil(LocalDateTime currentTime, LocalDateTime scheduledTime) {
        long hours = ChronoUnit.HOURS.between(currentTime, scheduledTime);
        if (hours < 24) {
            return hours + " Hours";
        } else {
            long days = ChronoUnit.DAYS.between(currentTime, scheduledTime);
            return days + " Days";
        }
    }

    private String formatBackupTime(Backup backup) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a 'Today'");
        return backup.getBackupTime().format(formatter);
    }
}
