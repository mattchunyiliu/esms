package kg.kundoluk.school.dto.section;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SectionInstructorScheduleResponse {
    private Long sectionInstructorId;
    private String sectionTitle;
    private Long personId;
    private String personTitle;
    private List<ScheduleWeekTime> scheduleWeekTimeList;
}
