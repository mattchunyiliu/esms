package kg.kundoluk.school.dto.report;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorCoursePerformanceReport {
    Long courseId;
    String courseTitle;
    String courseTitleRU;
    String courseTitleKG;
    Long instructorId;
    String instructorTitle;
    Integer quality;
    Integer achievement;
    Integer totalFive;
    Integer totalFour;
    Integer totalThree;
    Integer totalTwo;

    public InstructorCoursePerformanceReport(ClassInstructorCourseDTO instructorCourse) {
        this.courseId = instructorCourse.getCourseId();
        this.courseTitle = instructorCourse.getCourseTitle();
        this.courseTitleKG = instructorCourse.getCourseTitleKG();
        this.courseTitleRU = instructorCourse.getCourseTitleRU();
        this.instructorId = instructorCourse.getInstructorId();
        this.instructorTitle = instructorCourse.getInstructorTitle();
    }
}
