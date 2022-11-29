package kg.kundoluk.school.components.hateoas.resource;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class CalendarTopicResource extends RepresentationModel<CalendarTopicResource> {
    private Long id;
    private String title;
    private Integer hours;
    private Long quarterId;
    private Long calendarPlanId;
    private String datePlan;
    private String topicResult;
    private String topicVisibility;
    private Boolean isPassed;
}
