package kg.kundoluk.school.dto.chat;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatChannel implements Serializable {
    Long chatId;
    String channelId;
    Long senderId;
    String senderTitle;
    Long recipientId;
    String content;
}
