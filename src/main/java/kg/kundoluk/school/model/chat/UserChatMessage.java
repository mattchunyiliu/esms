package kg.kundoluk.school.model.chat;

import kg.kundoluk.school.model.base.TimedEntity;
import kg.kundoluk.school.model.enums.ChatMessageType;
import kg.kundoluk.school.model.enums.UploadFileType;
import kg.kundoluk.school.model.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "user_chat_message")
public class UserChatMessage extends TimedEntity {

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User senderUser;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipientUser;

    @Column(name = "contents")
    private String contents;

    @Column(name = "ip")
    private String ipAddress;

    @Column(name = "status")
    private Integer status;

    @Column(name = "chat_room")
    private String chatRoom;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "message_type")
    private ChatMessageType messageType;

    @Column(name = "attachment_url")
    private String attachmentUrl;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "attachment_type")
    private UploadFileType attachmentType;

    public UserChatMessage() {
    }

    public UserChatMessage(User senderUser, User recipientUser, String contents) {
        this.senderUser = senderUser;
        this.recipientUser = recipientUser;
        this.contents = contents;
    }
}
