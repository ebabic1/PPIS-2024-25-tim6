package ba.unsa.etf.ppis.tim6.controller;

import ba.unsa.etf.ppis.tim6.dto.BackupDTO;
import ba.unsa.etf.ppis.tim6.mapper.BackupMapper;
import ba.unsa.etf.ppis.tim6.model.Backup;
import ba.unsa.etf.ppis.tim6.repository.BackupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/backups")
@RequiredArgsConstructor
public class BackupController {

    private final BackupRepository backupRepository;
    private final BackupMapper backupMapper;

    @PostMapping
    public ResponseEntity<BackupDTO> createBackup(@RequestBody BackupDTO backupDTO) {
        Backup backup = backupMapper.backupDTOToBackup(backupDTO);
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
            backup.setBackupTime(backupDTO.getBackupTime());
            backup.setBackupLocation(backupDTO.getBackupLocation());
            backup.setStatus(backupDTO.getStatus());
            backup.setBackupSize(backupDTO.getBackupSize());
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
}
