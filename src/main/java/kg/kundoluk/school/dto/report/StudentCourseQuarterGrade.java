package kg.kundoluk.school.dto.report;

import kg.kundoluk.school.dto.projection.QuarterGradeStudentAnalyticDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourseQuarterGrade {
    Long courseId;
    Long instructorId;
    Long quarterId;
    Integer quarterType;
    String quarterMark;

    public StudentCourseQuarterGrade(QuarterGradeStudentAnalyticDTO quarterGrade) {
        this.courseId = quarterGrade.getCourseId();
        this.instructorId = quarterGrade.getInstructorId();
        this.quarterMark = quarterGrade.getQuarterMark();
        this.quarterId = quarterGrade.getQuarterId();
        this.quarterType = quarterGrade.getGradeType();
    }
}
