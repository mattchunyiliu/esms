package kg.kundoluk.school.model.student;

import kg.kundoluk.school.model.base.TimedEntity;
import kg.kundoluk.school.model.student.Student;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "card_attendance")
public class CardAttendance extends TimedEntity {

    private static final long serialVersionUID = 7981861386645224918L;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "attendance_type")
    private Integer attendanceType;

    @Column(name = "attendance_date")
    private LocalDateTime attendanceDate;
}
