package kg.kundoluk.school.dto.projection;

public interface GradeStudentAnalytic {
    String getStudentTitle();
    Long getCourseId();
    String getCourseTitle();
    String getCourseTitleRU();
    String getCourseTitleKG();
    Double getAverageGrade();
}
