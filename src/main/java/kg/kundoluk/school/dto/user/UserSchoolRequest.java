package kg.kundoluk.school.dto.user;

import kg.kundoluk.school.model.user.User;
import kg.kundoluk.school.model.school.School;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSchoolRequest {
    User user;
    School school;
    Boolean active;
    Integer startYear;
    Integer endYear;
}
