package kg.kundoluk.school.dto.schedule;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleMonthResponse {
    private String day;
    private Long shiftTimeId;
    private String shiftTimeTitle;
}
