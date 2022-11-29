package kg.kundoluk.school.dto.person;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PersonCreateRequest extends PersonAbstractDto{
    @NotNull
    private Long roleId;
    private Long schoolId;
    private String job;
    private String jobPlace;
}
