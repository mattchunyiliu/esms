package kg.kundoluk.school.mapper.auth;

import kg.kundoluk.school.dto.auth.AuthenticatedUserDto;
import kg.kundoluk.school.model.user.User;

public interface AuthMapper {
    AuthenticatedUserDto toAuthenticatedUserDto(User user);
}
