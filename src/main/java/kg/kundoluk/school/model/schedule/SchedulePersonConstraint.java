package kg.kundoluk.school.model.schedule;

import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.base.BaseEntity;
import kg.kundoluk.school.model.enums.WeekDay;
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
@Table(name = "schedule_person_constraint")
public class SchedulePersonConstraint extends BaseEntity {

    private static final long serialVersionUID = 1233860886872269444L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_time_id")
    private ShiftTime shiftTime;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "week_day")
    private WeekDay weekDay;

    @Column(name = "is_allowed")
    private Boolean isAllowed;
}
