package kg.kundoluk.school.model.grade;

import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.base.GradeBaseEntity;
import kg.kundoluk.school.model.enums.GradeMarkType;
import kg.kundoluk.school.model.instructor.CalendarTopic;
import kg.kundoluk.school.model.school.ShiftTime;
import kg.kundoluk.school.model.student.StudentCourse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain = true)
@Audited
@Entity
@Table(name = "grade")
public class Grade extends GradeBaseEntity {

    private static final long serialVersionUID = -2821371422779473691L;

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
    @JoinColumn(name = "topic_id")
    private CalendarTopic calendarTopic;

    @Column(name = "mark")
    private String mark;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "grade_type")
    private GradeMarkType gradeMarkType;

    @Column(name = "comment")
    private String comment;

    @Column(name = "grade_date")
    private LocalDate gradeDate;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_time_id")
    private ShiftTime shiftTime;
}
