package kg.kundoluk.school.dto.schedule;

import kg.kundoluk.school.model.enums.WeekDay;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleResponse {
    private Long id;
    private Long instructorId;
    private String instructorTitle;
    private Long courseId;
    private String courseTitle;
    private String courseTitleRU;
    private String courseTitleKG;
    private Long classId;
    private String classTitle;
    private Long shiftTimeId;
    private Boolean isGroup;
    private String groupTitle;
    private Long scheduleGroupId;
    private WeekDay weekDay;
    private Long schoolId;
}
