package kg.kundoluk.school.dto.schedule;

import kg.kundoluk.school.model.enums.WeekDay;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleCourseConstraintCreateRequest {
    private Long courseId;
    private Long shiftTimeId;
    private WeekDay weekDay;
}
