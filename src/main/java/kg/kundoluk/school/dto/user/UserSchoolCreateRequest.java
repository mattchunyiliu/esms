package kg.kundoluk.school.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserSchoolCreateRequest {
    @NotNull
    Long schoolId;
    @NotNull
    Long userId;
}
