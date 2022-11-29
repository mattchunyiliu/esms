package kg.kundoluk.school.dto.projection;

public interface GradeDTO {
    Long getGradeId();
    String getGradeDate();
    Long getStudentCourseId();
    Long getShiftTimeId();
    Long getTopicId();
    Integer getGradeMarkType();
    String getMark();
}
