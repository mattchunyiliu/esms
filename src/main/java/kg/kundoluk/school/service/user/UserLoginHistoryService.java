package kg.kundoluk.school.service.user;

import kg.kundoluk.school.model.enums.LoginType;
import kg.kundoluk.school.model.user.User;
import kg.kundoluk.school.model.user.UserLoginHistory;

public interface UserLoginHistoryService {
    UserLoginHistory create(User user, LoginType loginType);
}
