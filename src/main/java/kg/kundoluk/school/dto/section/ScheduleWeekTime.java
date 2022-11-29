package kg.kundoluk.school.dto.section;

import kg.kundoluk.school.model.enums.WeekDay;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleWeekTime {
    private Long id;
    private WeekDay weekDay;
    private String startTime;
    private String endTime;
}
