package kg.kundoluk.school.mapper.user;

import kg.kundoluk.school.dto.user.UserProfileDto;
import kg.kundoluk.school.model.user.User;

public interface UserMapper {
    UserProfileDto toUserProfileDto(User user);
}
