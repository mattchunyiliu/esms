package kg.kundoluk.school.model.grade;

import kg.kundoluk.school.model.base.TimedEntity;
import kg.kundoluk.school.model.student.Student;
import kg.kundoluk.school.model.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "grade_parent_check")
public class GradeParentCheck extends TimedEntity {

    private static final long serialVersionUID = -519245197082819295L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_checked")
    private Boolean checked;

    @Column(name = "week_number")
    private Integer weekNumber;
}
