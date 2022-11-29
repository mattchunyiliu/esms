package kg.kundoluk.school.dto.user;

import kg.kundoluk.school.model.enums.FormStatusType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserApplicationFormCreateRequest {
    private String firstName;
    private String lastName;
    private String middleName;
    private String comment;
    private String phoneNumber;
    private FormStatusType statusType;
    @NotNull
    private Long schoolId;
    @NotNull
    private Long roleId;
    private Long classId;
}
