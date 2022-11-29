package kg.kundoluk.school.dto.section;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectionStudentResponse {
    private Long id;
    private Long sectionInstructorId;
    private String sectionTitle;
    private String personTitle;
}
