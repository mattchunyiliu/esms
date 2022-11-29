package kg.kundoluk.school.dto.projection;

public interface StudentViewMobileDTO {
    Long getId();
    String getFirstName();
    String getLastName();
    String getMiddleName();
    String getUsername();
    String getAvatar();
    Integer getGender();
    Boolean getArchived();
    Integer getParentCount();
}
