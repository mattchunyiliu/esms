package kg.kundoluk.school.dto.projection;

public interface GradeMobileResponseDTO {
    Long getGradeId();
    String getGradeDate();
    Long getStudentCourseId();
    Integer getGradeMarkType();
    String getMark();
}
