package kg.kundoluk.school.dto.student.parent;

import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.student.Student;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentParentRequest {
    Person person;
    Student student;
    Integer parentalType;
}
