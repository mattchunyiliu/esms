package kg.kundoluk.school.service.report;

import kg.kundoluk.school.dto.report.MoveReportDto;
import kg.kundoluk.school.dto.report.MoveReportProjection;
import kg.kundoluk.school.model.MoveReport;

import java.util.List;

public interface MoveReportService {
    MoveReport create(MoveReportDto moveReportDto);
    MoveReport update(MoveReport moveReport, MoveReportDto moveReportDto);
    void delete(Long id);
    List<MoveReportProjection> findAllByQuarter(Long quarterId, Long classId);
}
