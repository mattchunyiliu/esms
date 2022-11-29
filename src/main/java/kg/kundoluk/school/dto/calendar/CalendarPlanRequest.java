package kg.kundoluk.school.dto.calendar;

import kg.kundoluk.school.model.references.ChronicleYear;
import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.school.SchoolClass;
import kg.kundoluk.school.model.school.SchoolCourse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CalendarPlanRequest {
    String title;
    Person person;
    SchoolClass schoolClass;
    SchoolCourse schoolCourse;
    ChronicleYear chronicleYear;
    Boolean isDefault;
}
