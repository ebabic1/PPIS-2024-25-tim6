package ba.unsa.etf.ppis.tim6.mapper;

import ba.unsa.etf.ppis.tim6.dto.BackupDTO;
import ba.unsa.etf.ppis.tim6.dto.CreateBackupDTO;
import ba.unsa.etf.ppis.tim6.model.Backup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BackupMapper {

    @Mapping(target = "backup_id", source = "backupId")
    @Mapping(target = "backup_time", source = "backupTime")
    @Mapping(target = "backup_size", source = "backupSize")
    @Mapping(target = "backup_location", source = "backupLocation")
    BackupDTO backupToBackupDTO(Backup backup);

    @Mapping(target = "backupId", ignore = true)
    @Mapping(target = "backupTime", source = "backup_time")
    @Mapping(target = "backupSize", source = "backup_size")
    @Mapping(target = "backupLocation", source = "backup_location")
    @Mapping(target = "status", expression = "java(determineStatus(createBackupDTO))")
    Backup createBackupDTOToBackup(CreateBackupDTO createBackupDTO);

    default Backup.Status determineStatus(CreateBackupDTO dto) {
        String backupTimeStr = String.valueOf(dto.getBackup_time());
        if (backupTimeStr != null && !backupTimeStr.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

                LocalDateTime localDateTime = LocalDateTime.parse(backupTimeStr, formatter);

                Instant backupInstant = localDateTime.toInstant(ZoneOffset.UTC);

                Instant now = Instant.now();

                if (backupInstant.isAfter(now)) {
                    return Backup.Status.PENDING;
                } else {
                    if (dto.getBackup_size() != null && !dto.getBackup_size().isEmpty()) {
                        return Backup.Status.SUCCESSFUL;
                    }
                }
            } catch (Exception ignored) {
            }
        }
        return Backup.Status.FAILED;
    }
}
