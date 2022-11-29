package kg.kundoluk.school.components.hateoas.resource;

import kg.kundoluk.school.model.enums.ChatMessageType;
import kg.kundoluk.school.model.enums.UploadFileType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class ChatResource extends RepresentationModel<ChatResource> {

    private Long id;
    private String contents;
    private Long senderId;
    private String senderName;
    private Long recipientId;
    private String recipientName;
    private String sentDate;
    private Integer status;
    private String chatRoom;
    private ChatMessageType messageType;
    private String attachmentUrl;
    private UploadFileType attachmentType;
}
