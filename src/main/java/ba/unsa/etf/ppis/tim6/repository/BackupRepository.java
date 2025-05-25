package ba.unsa.etf.ppis.tim6.repository;

import ba.unsa.etf.ppis.tim6.model.Backup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BackupRepository extends JpaRepository<Backup, Long> {
    Optional<Backup> findTopByStatusOrderByBackupTimeDesc(Backup.Status status);
    Optional<Backup> findFirstByBackupTimeGreaterThanOrderByBackupTimeAsc(LocalDateTime currentTime);
    List<Backup> findByStatusOrderByBackupTimeDesc(Backup.Status status);
}