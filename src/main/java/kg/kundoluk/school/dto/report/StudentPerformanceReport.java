package kg.kundoluk.school.dto.report;

import kg.kundoluk.school.dto.projection.StudentViewMobileDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentPerformanceReport {
    Long studentId;
    String studentTitle;
    Long chronicleId;
    Integer gender;
    String averageGrade;
    Integer quality;
    Integer achievement;
    Integer totalFive;
    Integer totalFour;
    Integer totalThree;
    Integer totalTwo;
    List<StudentCourseQuarterGrade> courseDtoList;

    public StudentPerformanceReport(StudentViewMobileDTO student) {
        this.studentId = student.getId();
        this.studentTitle = String.format("%s %s",student.getLastName(), student.getFirstName());
        this.gender = student.getGender();
    }
}
