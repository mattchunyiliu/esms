package kg.kundoluk.school.dto.calendar;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarTopicUpdateRequest {
    private String title;
    private Integer hours;
    private Long quarterId;
    private String datePlan;
    private String topicResult;
    private String topicVisibility;
    private Boolean isPassed;
}
