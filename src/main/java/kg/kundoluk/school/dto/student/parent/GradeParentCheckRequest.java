package kg.kundoluk.school.dto.student.parent;

import kg.kundoluk.school.model.student.Student;
import kg.kundoluk.school.model.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class GradeParentCheckRequest {
    Student student;
    User user;
    LocalDate startDate;
    LocalDate endDate;
    Boolean checked;
    Integer weekNumber;
}
