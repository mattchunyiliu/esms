package kg.kundoluk.school.model.schedule;

import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.base.TimedEntity;
import kg.kundoluk.school.model.enums.WeekDay;
import kg.kundoluk.school.model.school.SchoolClass;
import kg.kundoluk.school.model.school.SchoolCourse;
import kg.kundoluk.school.model.school.ShiftTime;
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
@Table(name = "schedule")
public class Schedule extends TimedEntity {

    private static final long serialVersionUID = 5729990636647327695L;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "week_day")
    private WeekDay weekDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private SchoolClass schoolClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_time_id")
    private ShiftTime shiftTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private SchoolCourse schoolCourse;

    @Column(name = "is_group")
    private Boolean isGroup;

    @Column(name = "group_title")
    private String groupTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_group_id")
    private ScheduleGroup scheduleGroup;
}
