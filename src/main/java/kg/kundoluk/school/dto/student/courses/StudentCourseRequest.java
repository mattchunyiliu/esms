package kg.kundoluk.school.dto.student.courses;

import kg.kundoluk.school.model.references.ChronicleYear;
import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.school.SchoolClass;
import kg.kundoluk.school.model.school.SchoolCourse;
import kg.kundoluk.school.model.student.Student;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentCourseRequest {
    Student student;
    SchoolClass schoolClass;
    SchoolCourse schoolCourse;
    Person person;
    ChronicleYear chronicleYear;
}
