package kg.kundoluk.school.model.section;

import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.base.TimedEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "section_instructor")
public class SectionInstructor extends TimedEntity {

    private static final long serialVersionUID = -8368818001451234890L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private Section section;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private Person person;

    @OneToMany(mappedBy = "sectionInstructor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<SectionSchedule> schedules;

    @OneToMany(mappedBy = "sectionInstructor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<SectionStudent> students;
}
