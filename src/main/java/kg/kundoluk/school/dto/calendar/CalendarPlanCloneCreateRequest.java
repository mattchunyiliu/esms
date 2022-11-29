package kg.kundoluk.school.dto.calendar;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarPlanCloneCreateRequest {
    private Long calendarPlanId;
    private String title;
    private Long classId;
    private Long chronicleId;
}
