package kg.kundoluk.school.dto.projection;

public interface AnalyticGender {
    Integer getMaleCount();
    Integer getFemaleCount();
    Long getSchoolId();
    String getSchoolName();
    Integer getSchoolType();
    Integer getSchoolOrganizationType();
    Integer getSchoolLanguageType();
    Long getRayonId();
    Long getRegionId();
    Long getTownId();
}
