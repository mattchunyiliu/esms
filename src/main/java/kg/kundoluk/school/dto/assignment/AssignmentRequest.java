package kg.kundoluk.school.dto.assignment;

import kg.kundoluk.school.model.instructor.CalendarTopic;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class AssignmentRequest {
    CalendarTopic calendarTopic;
    String title;
    String content;
    LocalDate deadlineAt;
}
