package kg.kundoluk.school.repository;

import kg.kundoluk.school.model.notification.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @EntityGraph(attributePaths = {"sender"})
    Page<Notification> findAllByRecipientId(Long userId, Pageable pageable);

    @Query(value = "select count(*) from notification where recipient_id = ?1 and notification_status = 0", nativeQuery = true)
    Integer countNotificationByRecipientId(Long id);

    @Modifying
    @Query(value = "UPDATE notification SET notification_status = ?2 WHERE id = ?1",nativeQuery = true)
    @Transactional
    void updateStatus(Long id, Integer status);

    @Modifying
    @Query(value = "UPDATE notification SET notification_status = 1 WHERE recipient_id = ?1 and notification_status = 0",nativeQuery = true)
    @Transactional
    void updateStatusAll(Long userId);
}
