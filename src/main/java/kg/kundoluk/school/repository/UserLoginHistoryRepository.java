package kg.kundoluk.school.repository;

import kg.kundoluk.school.model.user.UserLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, Long> {
}
