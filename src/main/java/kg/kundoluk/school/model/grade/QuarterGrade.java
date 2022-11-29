package kg.kundoluk.school.model.grade;

import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.base.QuarterGradeBaseEntity;
import kg.kundoluk.school.model.enums.QuarterGradeMarkType;
import kg.kundoluk.school.model.school.Quarter;
import kg.kundoluk.school.model.school.SchoolClass;
import kg.kundoluk.school.model.student.StudentCourse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;

@Getter
@Setter
@Accessors(chain = true)
@Audited
@Entity
@Table(name = "quarter_grade")
public class QuarterGrade extends QuarterGradeBaseEntity {

    private static final long serialVersionUID = -1839128731382669419L;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m2m_student_course_id")
    private StudentCourse studentCourse;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private Person person;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quarter_id")
    private Quarter quarter;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private SchoolClass schoolClass;

    @Column(name = "mark")
    private String mark;

    @Column(name = "comment")
    private String comment;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "grade_type")
    private QuarterGradeMarkType gradeMarkType;
}
