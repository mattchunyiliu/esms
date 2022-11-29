package kg.kundoluk.school.dto.chat;

public interface UserParentUnreadDTO {
    String getParentTitle();
    String getStudentTitle();
    Long getParentUserId();
    Long getStudentId();
    Integer getParentalType();
    Integer getUnreadCount();
    String getParentAvatar();

}
