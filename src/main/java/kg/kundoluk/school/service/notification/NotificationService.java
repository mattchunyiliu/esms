package kg.kundoluk.school.service.notification;

import kg.kundoluk.school.dto.notification.NotificationCreateRequest;
import kg.kundoluk.school.model.notification.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface NotificationService {
    Notification create(NotificationCreateRequest notificationCreateRequest);
    Page<Notification> findAllByUser(Long recipientId, Pageable pageable);
    Integer countUnreadByUser(Long userId, Authentication authentication);
    void makeRead(Long notificationId);
    void makeReadAll(Long userId);
    void delete(Long id);
}
