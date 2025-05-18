package ba.unsa.etf.ppis.tim6.mapper;

import ba.unsa.etf.ppis.tim6.dto.ReportDTO;
import ba.unsa.etf.ppis.tim6.model.Report;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReportMapper {

    @Mapping(target = "report_id", source = "reportId")
    @Mapping(target = "report_type", source = "reportType")
    @Mapping(target = "created_at", source = "createdAt", qualifiedByName = "formatDateTime")
    @Mapping(target = "created_by_name", source = "createdBy.username")
    ReportDTO reportToReportDTO(Report report);

    @Named("formatDateTime")
    public default String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    Report reportDTOToReport(ReportDTO ReportDTO);
}