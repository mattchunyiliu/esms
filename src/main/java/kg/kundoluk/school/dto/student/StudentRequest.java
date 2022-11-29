package kg.kundoluk.school.dto.student;

import kg.kundoluk.school.model.enums.SubscriptionType;
import kg.kundoluk.school.model.user.User;
import kg.kundoluk.school.model.enums.Gender;
import kg.kundoluk.school.model.school.SchoolClass;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class StudentRequest {
    String firstName;
    String lastName;
    String middleName;
    Gender gender;
    LocalDate birthday;
    String avatar;
    String nationality;
    User user;
    Boolean archived;
    SchoolClass schoolClass;
    SubscriptionType subscriptionType;
}
