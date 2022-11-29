package kg.kundoluk.school.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstablishedChatChannelRequest {
    private String channelUuid;
    private String userOneFullName;
    private String userTwoFullName;

    public EstablishedChatChannelRequest(String channelUuid, String userOneFullName, String userTwoFullName) {
        this.channelUuid = channelUuid;
        this.userOneFullName = userOneFullName;
        this.userTwoFullName = userTwoFullName;
    }
}
