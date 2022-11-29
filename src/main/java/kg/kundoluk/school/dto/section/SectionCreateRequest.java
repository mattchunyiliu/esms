package kg.kundoluk.school.dto.section;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectionCreateRequest {
    String title;
    String description;
    Long schoolId;
}
