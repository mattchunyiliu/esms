package kg.kundoluk.school.model.instructor;

import kg.kundoluk.school.model.references.ChronicleYear;
import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.base.TimedEntity;
import kg.kundoluk.school.model.school.SchoolClass;
import kg.kundoluk.school.model.school.SchoolCourse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "calendar_plan")
public class CalendarPlan extends TimedEntity {


    private static final long serialVersionUID = 5884291828579745703L;
    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private SchoolCourse schoolCourse;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private SchoolClass schoolClass;

    @Column(name = "is_default", columnDefinition = "boolean default false")
    private Boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "chronicle_id")
    private ChronicleYear chronicleYear;

    @OneToMany(mappedBy="calendarPlan")
    private Set<CalendarTopic> topicSet;
}
