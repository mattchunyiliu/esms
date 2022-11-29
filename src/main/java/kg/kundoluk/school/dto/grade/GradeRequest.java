package kg.kundoluk.school.dto.grade;

import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.enums.GradeMarkType;
import kg.kundoluk.school.model.instructor.CalendarTopic;
import kg.kundoluk.school.model.school.ShiftTime;
import kg.kundoluk.school.model.student.StudentCourse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class GradeRequest {
    Person person;
    StudentCourse studentCourse;
    LocalDate gradeDate;
    CalendarTopic calendarTopic;
    ShiftTime shiftTime;
    GradeMarkType gradeMarkType;
    String mark;
}
