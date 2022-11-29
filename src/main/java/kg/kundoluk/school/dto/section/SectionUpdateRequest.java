package kg.kundoluk.school.dto.section;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectionUpdateRequest {
    String title;
    String description;
}
