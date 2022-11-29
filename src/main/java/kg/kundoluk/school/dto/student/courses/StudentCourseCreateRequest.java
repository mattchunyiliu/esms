package kg.kundoluk.school.dto.student.courses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourseCreateRequest {
    private Long studentId;
    private Long courseId;
    private Long personId;
    private Long chronicleId;
    private Long classId;
}
