package kg.kundoluk.school.dto.schedule;

import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.enums.WeekDay;
import kg.kundoluk.school.model.school.ShiftTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SchedulePersonConstraintRequest {
    Person person;
    ShiftTime shiftTime;
    WeekDay weekDay;
}
