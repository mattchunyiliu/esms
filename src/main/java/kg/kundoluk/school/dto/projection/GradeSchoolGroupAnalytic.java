package kg.kundoluk.school.dto.projection;

public interface GradeSchoolGroupAnalytic {
    Long getSchoolId();
    String getSchoolName();
    Integer getSchoolOrganizationType();
    Integer getTotalCount();
    String getAverageGrade();
}
