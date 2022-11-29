package kg.kundoluk.school.model.notification;

import kg.kundoluk.school.dto.variables.NotificationStatusType;
import kg.kundoluk.school.dto.variables.NotificationType;
import kg.kundoluk.school.model.base.TimedEntity;
import kg.kundoluk.school.model.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "notification")
public class Notification extends TimedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "notification_type")
    private NotificationType notificationType;

    @Column(name = "contents")
    private String contents;

    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "notification_status")
    private NotificationStatusType status;

    @Column(name = "url")
    private String url;

    public Notification() {
    }

    public Notification(NotificationType notificationType, String contents, String title, NotificationStatusType status, String url) {
        this.notificationType = notificationType;
        this.contents = contents;
        this.title = title;
        this.status = status;
        this.url = url;
    }
}
