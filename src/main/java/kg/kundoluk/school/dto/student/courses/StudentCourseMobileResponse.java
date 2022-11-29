package kg.kundoluk.school.dto.student.courses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourseMobileResponse {
    private Long studentId;
    private Long studentCourseId;
    private Long schoolCourseId;
    private Long courseId;
    private String courseTitle;
    private String courseTitleRU;
    private String courseTitleKG;
    private Long instructorId;
    private String instructorTitle;
    private Long instructorUserId;
    private Long classId;
}
