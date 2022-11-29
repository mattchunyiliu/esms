package kg.kundoluk.school.dto.grade;

import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.enums.QuarterGradeMarkType;
import kg.kundoluk.school.model.school.Quarter;
import kg.kundoluk.school.model.school.SchoolClass;
import kg.kundoluk.school.model.student.StudentCourse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuarterGradeRequest {
    Person person;
    StudentCourse studentCourse;
    Quarter quarter;
    SchoolClass schoolClass;
    QuarterGradeMarkType gradeMarkType;
    String mark;
    String comment;

}
