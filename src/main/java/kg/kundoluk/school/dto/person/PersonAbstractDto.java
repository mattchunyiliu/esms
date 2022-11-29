package kg.kundoluk.school.dto.person;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonAbstractDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private Integer gender = 1;
    private String dateOfBirth = "1959-01-01";
    private String phone;
}
