package kg.kundoluk.school.dto.calendar;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CalendarPlanResponse implements Serializable {
    private Long id;
    private String title;
    private String courseTitle;
    private String courseTitleRU;
    private String courseTitleKG;
    private String classTitle;
    private Long courseId;
    private Long classId;
    private Long schoolId;
    private Integer topicTotalCount;
    private Integer topicTotalHour;
}
