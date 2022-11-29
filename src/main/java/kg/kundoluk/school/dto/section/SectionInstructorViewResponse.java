package kg.kundoluk.school.dto.section;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SectionInstructorViewResponse {
    private Long sectionId;
    private String sectionTitle;
    private List<SectionInstructorResponse> sectionInstructorResponses;
}
