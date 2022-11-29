package kg.kundoluk.school.dto.person;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonUpdateRequest extends PersonAbstractDto{
    private String job;
    private String jobPlace;
}
