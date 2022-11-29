package kg.kundoluk.school.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatChannelInitializationRequest {

    private Long userIdOne;
    private Long userIdTwo;

    public ChatChannelInitializationRequest() {
    }

    public ChatChannelInitializationRequest(Long userIdOne, Long userIdTwo) {
        this.userIdOne = userIdOne;
        this.userIdTwo = userIdTwo;
    }
}
