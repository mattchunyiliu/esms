package kg.kundoluk.school.mapper.user.impl;

import kg.kundoluk.school.dto.role.RoleShortDto;
import kg.kundoluk.school.dto.school.SchoolBaseDto;
import kg.kundoluk.school.dto.user.UserProfileDto;
import kg.kundoluk.school.dto.user.UserShortDto;
import kg.kundoluk.school.mapper.user.UserMapper;
import kg.kundoluk.school.model.user.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserProfileDto toUserProfileDto(User user) {
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setUser(new UserShortDto(user));
        userProfileDto.setRole(user.getRoles().stream().map(r-> new RoleShortDto(r.getId(), r.getCode())).collect(Collectors.toList()));
        userProfileDto.setSchools(user.getSchools().stream().map(s->new SchoolBaseDto(s.getId(), s.getName(), s.getAddress())).collect(Collectors.toList()));
        return userProfileDto;
    }
}
