package kg.kundoluk.school.service.notification.impl;

import kg.kundoluk.school.dto.notification.NotificationCreateRequest;
import kg.kundoluk.school.dto.variables.NotificationStatusType;
import kg.kundoluk.school.model.notification.Notification;
import kg.kundoluk.school.repository.NotificationRepository;
import kg.kundoluk.school.repository.UserRepository;
import kg.kundoluk.school.security.jwt.JwtUser;
import kg.kundoluk.school.service.notification.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Notification create(NotificationCreateRequest notificationCreateRequest) {
        Notification notification = new Notification(notificationCreateRequest.getNotificationType(), notificationCreateRequest.getContents(), notificationCreateRequest.getTitle(), notificationCreateRequest.getStatus(), notificationCreateRequest.getUrl());
        if (notificationCreateRequest.getSenderId()!=null)
            notification.setSender(userRepository.getOne(notificationCreateRequest.getSenderId()));
        if (notificationCreateRequest.getRecipientId()!=null)
            notification.setRecipient(userRepository.getOne(notificationCreateRequest.getRecipientId()));
        return notificationRepository.save(notification);
    }

    @Override
    public Page<Notification> findAllByUser(Long recipientId, Pageable pageable) {
        return notificationRepository.findAllByRecipientId(recipientId, pageable);
    }

    @Override
    public Integer countUnreadByUser(Long userId, Authentication authentication) {
        if (userId == null) {
            JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
            userId = jwtUser.getId();
        }
        return notificationRepository.countNotificationByRecipientId(userId);
    }

    @Override
    public void makeRead(Long notificationId) {
        notificationRepository.updateStatus(notificationId, NotificationStatusType.READ.ordinal());
    }

    @Override
    public void makeReadAll(Long userId) {
        notificationRepository.updateStatusAll(userId);
    }

    @Override
    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }
}
