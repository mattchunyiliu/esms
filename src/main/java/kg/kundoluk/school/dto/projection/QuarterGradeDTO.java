package kg.kundoluk.school.dto.projection;

public interface QuarterGradeDTO {
    Long getGradeId();
    Long getStudentCourseId();
    Long getQuarterId();
    Integer getGradeMarkType();
    String getMark();
}
