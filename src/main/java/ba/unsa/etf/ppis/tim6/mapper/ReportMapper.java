package ba.unsa.etf.ppis.tim6.mapper;

import ba.unsa.etf.ppis.tim6.dto.ReportDTO;
import ba.unsa.etf.ppis.tim6.model.Report;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReportMapper {

    ReportDTO reportToReportDTO(Report report);

    Report reportDTOToReport(ReportDTO ReportDTO);
}