package kg.kundoluk.school.dto.schedule;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleUpdateRequest {
    private Long instructorId;
    private Long courseId;
    private Boolean isGroup;
    private String groupTitle;
    private Long scheduleGroupId;
}
