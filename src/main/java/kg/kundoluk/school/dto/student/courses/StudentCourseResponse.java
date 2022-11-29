package kg.kundoluk.school.dto.student.courses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourseResponse {
    private Long id;
    private Long courseId;
    private Long instructorId;
    private Long classId;
}
