package kg.kundoluk.school.dto.section;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SectionInstructorScheduleUpdateRequest {
    Long instructorId;
    List<ScheduleWeekTime> scheduleWeekTimeList;
}
