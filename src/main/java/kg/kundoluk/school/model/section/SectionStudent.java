package kg.kundoluk.school.model.section;

import kg.kundoluk.school.model.references.ChronicleYear;
import kg.kundoluk.school.model.base.TimedEntity;
import kg.kundoluk.school.model.student.Student;
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
@Table(name = "section_student")
public class SectionStudent extends TimedEntity {


    private static final long serialVersionUID = 6305625039934191916L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_instructor_id")
    private SectionInstructor sectionInstructor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chronicle_id")
    private ChronicleYear chronicleYear;
}
