package kg.kundoluk.school.dto.schedule;

import kg.kundoluk.school.model.school.SchoolClass;
import kg.kundoluk.school.model.student.Student;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class ScheduleGroupRequest {
    SchoolClass schoolClass;
    String title;
    Set<Student> students;
}
