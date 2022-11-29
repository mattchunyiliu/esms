package kg.kundoluk.school.dto.schedule;

import kg.kundoluk.school.model.enums.WeekDay;
import kg.kundoluk.school.model.school.SchoolCourse;
import kg.kundoluk.school.model.school.ShiftTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScheduleCourseConstraintRequest {
    SchoolCourse schoolCourse;
    ShiftTime shiftTime;
    WeekDay weekDay;
}
