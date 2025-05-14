package ba.unsa.etf.ppis.tim6.mapper;

import ba.unsa.etf.ppis.tim6.dto.BackupDTO;
import ba.unsa.etf.ppis.tim6.model.Backup;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BackupMapper {

    BackupDTO backupToBackupDTO(Backup backup);

    Backup backupDTOToBackup(BackupDTO backupDTO);
}