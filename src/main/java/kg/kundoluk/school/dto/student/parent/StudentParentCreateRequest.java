package kg.kundoluk.school.dto.student.parent;

import kg.kundoluk.school.dto.person.PersonDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentParentCreateRequest {
    private PersonDto personDto;
    private Long studentId;
    private Integer parentalType;
}
