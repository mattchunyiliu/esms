package kg.kundoluk.school.dto.schedule;

import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.enums.WeekDay;
import kg.kundoluk.school.model.schedule.ScheduleGroup;
import kg.kundoluk.school.model.school.SchoolClass;
import kg.kundoluk.school.model.school.SchoolCourse;
import kg.kundoluk.school.model.school.ShiftTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScheduleRequest {
    WeekDay weekDay;
    Person person;
    SchoolClass schoolClass;
    ShiftTime shiftTime;
    SchoolCourse schoolCourse;
    Boolean isGroup;
    String groupTitle;
    ScheduleGroup scheduleGroup;
}
