package kg.kundoluk.school.dto.projection;

public interface QuarterGradeStudentAnalyticDTO {
    String getStudentTitle();
    Long getStudentId();
    Integer getStudentGender();
    Long getInstructorId();
    Long getCourseId();
    String getCourseTitle();
    String getCourseTitleKG();
    String getCourseTitleRU();
    Long getQuarterId();
    String getQuarterMark();
    Integer getGradeType();
}
