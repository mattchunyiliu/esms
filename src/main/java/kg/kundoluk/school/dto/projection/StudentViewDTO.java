package kg.kundoluk.school.dto.projection;

public interface StudentViewDTO {
    Long getId();
    String getFirstName();
    String getLastName();
    String getMiddleName();
    Integer getGender();
    String getPhone();
    Long getClassId();
    String getClassTitle();
    Boolean getArchived();
    Integer getSubscriptionType();
}
