package kg.kundoluk.school.dto.schedule;

import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.school.SchoolClass;
import kg.kundoluk.school.model.school.SchoolCourse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScheduleClassLoadRequest {
    SchoolClass schoolClass;
    Person person;
    SchoolCourse schoolCourse;
    Integer hourCount;
}
