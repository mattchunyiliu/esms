package kg.kundoluk.school.dto.calendar;

import kg.kundoluk.school.model.school.Quarter;
import kg.kundoluk.school.model.instructor.CalendarPlan;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CalendarTopicRequest {
    String title;
    Integer hours;
    Quarter quarter;
    CalendarPlan calendarPlan;
    LocalDate datePlan;
    String topicResult;
    String topicVisibility;
    Boolean isPassed;
}
