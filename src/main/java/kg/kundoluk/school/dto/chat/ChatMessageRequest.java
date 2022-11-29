package kg.kundoluk.school.dto.chat;

import kg.kundoluk.school.model.enums.ChatMessageType;
import kg.kundoluk.school.model.enums.UploadFileType;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ChatMessageRequest {

    private Long chatId;
    private String contents;
    private Long senderId;
    private String senderFullName;
    private Long recipientId;
    private String channelId;
    private String channelTitle;
    private Integer flag;
    private Integer actionType;
    private String sendTime;
    private ChatMessageType messageType = ChatMessageType.CHAT;
    private String attachmentUrl;
    private UploadFileType attachmentType;

}
