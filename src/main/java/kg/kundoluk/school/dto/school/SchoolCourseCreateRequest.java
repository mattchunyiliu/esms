package kg.kundoluk.school.dto.school;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolCourseCreateRequest {
    Long schoolId;
    Long courseId;
}
