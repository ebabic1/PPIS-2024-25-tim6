package ba.unsa.etf.ppis.tim6.mapper;

import ba.unsa.etf.ppis.tim6.dto.BackupDTO;
import ba.unsa.etf.ppis.tim6.dto.CreateBackupDTO;
import ba.unsa.etf.ppis.tim6.model.Backup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BackupMapper {

    @Mapping(target = "backup_id", source = "backupId")
    @Mapping(target = "backup_time", source = "backupTime")
    @Mapping(target = "backup_size", source = "backupSize")
    @Mapping(target = "backup_location", source = "backupLocation")
    BackupDTO backupToBackupDTO(Backup backup);

    @Mapping(target = "backupId", ignore = true)
    @Mapping(target = "status", constant = "SUCCESSFUL")
    @Mapping(target = "backupTime", source = "backup_time")
    @Mapping(target = "backupSize", source = "backup_size")
    @Mapping(target = "backupLocation", source = "backup_location")
    Backup createBackupDTOToBackup(CreateBackupDTO createBackupDTO);
}