package kg.kundoluk.school.dto.projection;

public interface SchoolPresetDTO {
    Long getId();
    Long getSchoolId();
    String getSchoolName();
    Boolean getPreset();
    Boolean getIsClassRaised();
    Integer getStepNumber();
}
