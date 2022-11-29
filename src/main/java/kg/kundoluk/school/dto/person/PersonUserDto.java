package kg.kundoluk.school.dto.person;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonUserDto extends PersonAbstractDto{
    private Long id;
    private Long userId;
    private String avatar;
    private String job;
    private String jobPlace;
}
