package kg.kundoluk.school.model.instructor;

import kg.kundoluk.school.model.school.Quarter;
import kg.kundoluk.school.model.base.TimedEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "calendar_topic")
public class CalendarTopic extends TimedEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "hours")
    private Integer hours;

    @Column(name = "date_plan")
    private LocalDate datePlan;

    @Column(name = "date_fact")
    private LocalDate dateFact;

    @Column(name = "topic_result")
    private String topicResult;

    @Column(name = "topic_visibility")
    private String topicVisibility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quarter_id")
    private Quarter quarter;

    @ManyToOne
    @JoinColumn(name = "calendar_plan_id")
    private CalendarPlan calendarPlan;

    @Column(name = "is_passed")
    private Boolean isPassed;
}
