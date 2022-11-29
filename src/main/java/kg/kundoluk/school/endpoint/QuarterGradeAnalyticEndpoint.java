package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.projection.GradeClassGroupAverage;
import kg.kundoluk.school.dto.projection.QuarterGradeGroupMarkDTO;
import kg.kundoluk.school.dto.projection.QuarterGradeStudentAnalyticDTO;
import kg.kundoluk.school.dto.projection.QuarterGradeStudentGroupMarkDTO;
import kg.kundoluk.school.dto.report.ClassQuarterGradeReportResponse;
import kg.kundoluk.school.model.enums.QuarterGradeMarkType;

import java.util.List;

public interface QuarterGradeAnalyticEndpoint {
    List<QuarterGradeStudentAnalyticDTO> getAllStudentAnalytic(Long classId, Long quarterId, Long courseId, QuarterGradeMarkType quarterGradeMarkType, Long chronicleId);
    List<QuarterGradeGroupMarkDTO> getStudentGroupByMark(Long schoolId, Long classId, Long quarterId);
    List<QuarterGradeStudentGroupMarkDTO> getStudentsGroupByMark(Long schoolId, Long classId, Long quarterId);
    List<GradeClassGroupAverage> getClassAverageGrade(Long schoolId, Long quarterId);
    ClassQuarterGradeReportResponse getClassPerformance(Long classId, Long quarterId, QuarterGradeMarkType quarterGradeMarkType, Long chronicleId);
}
