package kg.kundoluk.school.model.schedule;

import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.school.SchoolClass;
import kg.kundoluk.school.model.school.SchoolCourse;
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
@Table(name = "schedule_class_load")
public class ScheduleClassLoad {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_class_load_seq_gen")
    @SequenceGenerator(name = "schedule_class_load_seq_gen", sequenceName = "schedule_class_load_id_seq", allocationSize = 10)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private SchoolClass schoolClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private SchoolCourse schoolCourse;

    @Column(name = "hour_load")
    private Integer hourLoad;
}
