package kg.kundoluk.school.service.user.impl;

import kg.kundoluk.school.model.enums.LoginType;
import kg.kundoluk.school.model.user.User;
import kg.kundoluk.school.model.user.UserLoginHistory;
import kg.kundoluk.school.repository.UserLoginHistoryRepository;
import kg.kundoluk.school.service.user.UserLoginHistoryService;
import org.springframework.stereotype.Service;

@Service
public class UserLoginHistoryServiceImpl implements UserLoginHistoryService {
    private final UserLoginHistoryRepository userLoginHistoryRepository;

    public UserLoginHistoryServiceImpl(UserLoginHistoryRepository userLoginHistoryRepository) {
        this.userLoginHistoryRepository = userLoginHistoryRepository;
    }

    @Override
    public UserLoginHistory create(User user, LoginType loginType) {
        UserLoginHistory userLoginHistory = UserLoginHistory.builder()
                .user(user)
                .loginType(loginType)
                .build();
        return userLoginHistoryRepository.save(userLoginHistory);
    }
}
