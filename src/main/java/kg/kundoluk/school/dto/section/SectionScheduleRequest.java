package kg.kundoluk.school.dto.section;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SectionScheduleRequest {
    private Long sectionInstructorId;
    private ScheduleWeekTime scheduleWeekTime;
}
