package kg.kundoluk.school.dto.student;

import kg.kundoluk.school.model.references.ChronicleYear;
import kg.kundoluk.school.model.student.Student;
import kg.kundoluk.school.model.school.SchoolClass;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentClassRequest {
    Student student;
    SchoolClass schoolClass;
    ChronicleYear chronicleYear;
}
