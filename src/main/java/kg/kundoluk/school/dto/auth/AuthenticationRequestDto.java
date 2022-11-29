package kg.kundoluk.school.dto.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class AuthenticationRequestDto {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
