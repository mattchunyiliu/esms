package kg.kundoluk.school.components.hateoas.resource;

import kg.kundoluk.school.dto.variables.NotificationStatusType;
import kg.kundoluk.school.dto.variables.NotificationType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class NotificationResource extends RepresentationModel<NotificationResource> {
    public  Long id;
    private String title;
    private String content;
    private NotificationStatusType notificationStatusType;
    private NotificationType notificationType;
    private String createdAt;
    private String senderTitle;
}
