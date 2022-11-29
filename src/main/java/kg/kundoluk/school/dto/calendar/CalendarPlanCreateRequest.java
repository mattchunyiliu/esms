package kg.kundoluk.school.dto.calendar;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarPlanCreateRequest {
    private String title;
    private Long personId;
    private Long courseId;
    private Long classId;
    private Long chronicleId;
    private Boolean isDefault = false;
}
