package kg.kundoluk.school.dto.user;

import kg.kundoluk.school.model.references.Role;
import kg.kundoluk.school.model.enums.FormStatusType;
import kg.kundoluk.school.model.school.School;
import kg.kundoluk.school.model.school.SchoolClass;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class UserApplicationFormRequest {
    String firstName;
    String lastName;
    String middleName;
    String comment;
    String phoneNumber;
    FormStatusType statusType;
    @NotNull
    private School school;
    @NotNull
    private Role role;
    private SchoolClass schoolClass;
}
