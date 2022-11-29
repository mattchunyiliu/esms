package kg.kundoluk.school.dto.section;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SectionInstructorScheduleCreateRequest {
    Long sectionId;
    Long instructorId;
    List<ScheduleWeekTime> scheduleWeekTimeList;
}
