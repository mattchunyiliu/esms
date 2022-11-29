package kg.kundoluk.school.model.section;

import kg.kundoluk.school.model.base.TimedEntity;
import kg.kundoluk.school.model.enums.WeekDay;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "section_schedule")
public class SectionSchedule extends TimedEntity {

    private static final long serialVersionUID = 5562222275797592784L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_instructor_id")
    private SectionInstructor sectionInstructor;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "week_day")
    private WeekDay weekDay;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

}
