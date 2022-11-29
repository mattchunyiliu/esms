package kg.kundoluk.school.dto.calendar;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarTopicResponse {
    private Long id;
    private String title;
    private Long quarterId;
    private String quarterSemester;
    private String topicDate;
    private Integer topicHour;
    private String topicResult;
    private String topicVisibility;
    private Boolean isPassed;
}
