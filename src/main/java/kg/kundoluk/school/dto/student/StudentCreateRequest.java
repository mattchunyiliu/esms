package kg.kundoluk.school.dto.student;

import kg.kundoluk.school.dto.person.PersonAbstractDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class StudentCreateRequest extends PersonAbstractDto {
    @NotNull
    private Long schoolId;
    @NotNull
    private Long classId;
    private String nationality;
}
