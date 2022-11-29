package kg.kundoluk.school.dto.user;

import kg.kundoluk.school.model.references.Language;
import kg.kundoluk.school.model.references.Role;
import kg.kundoluk.school.model.location.Rayon;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class UserRequest {
    String firstName;
    String lastName;
    String middleName;
    String phone;
    String username;
    String password;
    String avatar;
    Set<Role> roles;
    Set<Rayon> rayons;
    Language language;
}
