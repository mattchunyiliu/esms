package kg.kundoluk.school.dto.schoolClass;

import kg.kundoluk.school.model.enums.ClassType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassCreateRequest {
    String label;
    Integer level;
    ClassType classType;
    Long schoolId;
    Long shiftId;
    Long languageId;
    Long personId;
}
