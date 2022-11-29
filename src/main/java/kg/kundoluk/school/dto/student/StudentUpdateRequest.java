package kg.kundoluk.school.dto.student;

import kg.kundoluk.school.dto.person.PersonAbstractDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentUpdateRequest extends PersonAbstractDto {
    private String nationality;
    private Long classId;
    private Boolean archived = false;

}
