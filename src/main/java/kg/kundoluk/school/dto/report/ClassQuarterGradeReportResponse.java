package kg.kundoluk.school.dto.report;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClassQuarterGradeReportResponse {
    List<StudentPerformanceReport> studentPerformanceReports;
    List<InstructorCoursePerformanceReport> instructorCoursePerformanceReports;

    public ClassQuarterGradeReportResponse(List<StudentPerformanceReport> studentPerformanceReports, List<InstructorCoursePerformanceReport> instructorCoursePerformanceReports) {
        this.studentPerformanceReports = studentPerformanceReports;
        this.instructorCoursePerformanceReports = instructorCoursePerformanceReports;
    }
}
