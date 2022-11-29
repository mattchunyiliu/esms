package kg.kundoluk.school.dto.school;

import kg.kundoluk.school.model.references.Course;
import kg.kundoluk.school.model.school.School;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SchoolCourseRequest {
    School school;
    Course course;
}
