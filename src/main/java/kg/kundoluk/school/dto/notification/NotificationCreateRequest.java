package kg.kundoluk.school.dto.notification;

import kg.kundoluk.school.dto.variables.NotificationStatusType;
import kg.kundoluk.school.dto.variables.NotificationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationCreateRequest {
    private String title;
    private String url;
    private String senderTitle;
    private Long recipientId;
    private Long senderId;
    private NotificationType notificationType;
    private String contents;
    private NotificationStatusType status;

    public NotificationCreateRequest(NotificationType notificationType, NotificationStatusType status) {
        this.notificationType = notificationType;
        this.status = status;
    }
}
