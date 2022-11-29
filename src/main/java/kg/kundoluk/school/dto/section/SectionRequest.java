package kg.kundoluk.school.dto.section;

import kg.kundoluk.school.model.school.School;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SectionRequest {
    String title;
    String description;
    School school;
}
