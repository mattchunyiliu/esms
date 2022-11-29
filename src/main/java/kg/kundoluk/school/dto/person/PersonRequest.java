package kg.kundoluk.school.dto.person;

import kg.kundoluk.school.model.user.User;
import kg.kundoluk.school.model.enums.Gender;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PersonRequest {
    String firstName;
    String lastName;
    String middleName;
    String phone;
    Gender gender;
    LocalDate birthday;
    String job;
    String jobPlace;
    String avatar;
    User user;
}
