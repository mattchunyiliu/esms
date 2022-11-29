package kg.kundoluk.school.dto.chat;

public interface UserChannelDTO {
    String getChannelUuid();
    Long getUserIdOne();
    String getUserTitleOne();
    Long getUserIdTwo();
    String getUserTitleTwo();
    Integer getUnreadCount();
}
