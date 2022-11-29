package kg.kundoluk.school.dto.section;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectionInstructorResponse {
    private Long sectionInstructorId;
    private String sectionTitle;
    private Long personId;
    private String personTitle;
    private Integer studentCount;

    public SectionInstructorResponse() {
    }

    public SectionInstructorResponse(Long sectionInstructorId, String personTitle, Integer studentCount) {
        this.sectionInstructorId = sectionInstructorId;
        this.personTitle = personTitle;
        this.studentCount = studentCount;
    }
}
