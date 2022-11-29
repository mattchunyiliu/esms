package kg.kundoluk.school.dto.calendar;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarPlanUpdateRequest {
    private String title;
    private Long courseId;
    private Long classId;
}
