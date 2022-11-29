package kg.kundoluk.school.dto.school;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SchoolCourseDto implements Serializable {
    private Long courseId;
    private String courseTitle;
    private String courseTitleKG;
    private String courseTitleRU;

    public SchoolCourseDto(Long courseId, String courseTitle, String courseTitleKG, String courseTitleRU) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.courseTitleKG = courseTitleKG;
        this.courseTitleRU = courseTitleRU;
    }
}
