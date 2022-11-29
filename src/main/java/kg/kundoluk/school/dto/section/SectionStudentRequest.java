package kg.kundoluk.school.dto.section;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectionStudentRequest {
    Long sectionInstructorId;
    Long studentId;
    Long chronicleId;
}
