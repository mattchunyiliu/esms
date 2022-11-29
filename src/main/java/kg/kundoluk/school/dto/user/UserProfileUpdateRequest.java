package kg.kundoluk.school.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileUpdateRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String newPhone;
    private Boolean isPhoneEdit;
    private String phone;
}
