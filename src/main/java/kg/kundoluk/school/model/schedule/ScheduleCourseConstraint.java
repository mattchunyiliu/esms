package kg.kundoluk.school.model.schedule;

import kg.kundoluk.school.model.base.BaseEntity;
import kg.kundoluk.school.model.enums.WeekDay;
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
@Table(name = "schedule_course_constraint")
public class ScheduleCourseConstraint extends BaseEntity {

    private static final long serialVersionUID = -2445208705642922794L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private SchoolCourse schoolCourse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_time_id")
    private ShiftTime shiftTime;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "week_day")
    private WeekDay weekDay;

    @Column(name = "is_allowed")
    private Boolean isAllowed;
}
