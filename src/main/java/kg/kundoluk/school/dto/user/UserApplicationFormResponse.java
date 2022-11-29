package kg.kundoluk.school.dto.user;

import kg.kundoluk.school.model.enums.FormStatusType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserApplicationFormResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String comment;
    private String phoneNumber;
    private FormStatusType statusType;
    private String schoolTitle;
    private String roleTitle;
    private String classTitle;
}
