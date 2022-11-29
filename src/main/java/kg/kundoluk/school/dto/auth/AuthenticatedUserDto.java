package kg.kundoluk.school.dto.auth;

import kg.kundoluk.school.dto.projection.SchoolDTO;
import kg.kundoluk.school.dto.role.RoleShortDto;
import kg.kundoluk.school.dto.user.UserShortDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthenticatedUserDto {
    private UserShortDto user;
    private String token;
    private String tokenType;
    private List<RoleShortDto> roles;
    private List<SchoolDTO> schools;

    public AuthenticatedUserDto() {
    }
    public AuthenticatedUserDto(String token) {
        this.token = token;
        this.tokenType = "Bearer";
    }
}
