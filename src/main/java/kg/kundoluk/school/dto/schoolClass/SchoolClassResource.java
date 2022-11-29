package kg.kundoluk.school.dto.schoolClass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolClassResource {
    Long id;
    String classLabel;
    Integer classLevel;
    String language;
    String languageTitle;
    Long shiftId;
    String shift;
    String personTitle;
}
