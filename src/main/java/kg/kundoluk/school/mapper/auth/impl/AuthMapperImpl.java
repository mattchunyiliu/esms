package kg.kundoluk.school.mapper.auth.impl;

import kg.kundoluk.school.dto.auth.AuthenticatedUserDto;
import kg.kundoluk.school.dto.role.RoleShortDto;
import kg.kundoluk.school.dto.user.UserShortDto;
import kg.kundoluk.school.mapper.auth.AuthMapper;
import kg.kundoluk.school.model.user.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AuthMapperImpl implements AuthMapper {
    @Override
    public AuthenticatedUserDto toAuthenticatedUserDto(User user) {
        AuthenticatedUserDto authenticatedUserDto = new AuthenticatedUserDto();
        authenticatedUserDto.setUser(new UserShortDto(user));
        authenticatedUserDto.setRoles(user.getRoles().stream().map(r-> new RoleShortDto(r.getId(), r.getCode())).collect(Collectors.toList()));
        return authenticatedUserDto;
    }
}
