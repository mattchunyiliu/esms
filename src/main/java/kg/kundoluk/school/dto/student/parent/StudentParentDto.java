package kg.kundoluk.school.dto.student.parent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentParentDto {
    Long id;
    Long studentId;
    Long parentId;
    Integer parentalType;
}
