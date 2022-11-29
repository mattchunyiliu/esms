package kg.kundoluk.school.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSwitchRole {
    Long schoolId;
    Long userId;
    Long roleId;
}
