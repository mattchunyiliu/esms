package kg.kundoluk.school.model.section;

import kg.kundoluk.school.model.base.TimedEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "section_student_grade")
public class SectionStudentGrade extends TimedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_student_id")
    private SectionStudent sectionStudent;

    @Column(name = "mark")
    private String mark;

    @Column(name = "grade_date")
    private LocalDate gradeDate;

    @Column(name = "topic")
    private String topic;
}
