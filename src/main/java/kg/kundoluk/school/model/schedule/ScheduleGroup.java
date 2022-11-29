package kg.kundoluk.school.model.schedule;

import kg.kundoluk.school.model.base.TimedEntity;
import kg.kundoluk.school.model.school.SchoolClass;
import kg.kundoluk.school.model.student.Student;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "schedule_group")
public class ScheduleGroup extends TimedEntity {

    private static final long serialVersionUID = -1215850296477115909L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private SchoolClass schoolClass;


    @Column(name = "title")
    private String groupTitle;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "m2m_schedule_group_student",
            joinColumns = {@JoinColumn(name = "schedule_group_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")})
    private Set<Student> students = new HashSet<>();

    public void addStudents(Set<Student> studentList) {
        students.addAll(studentList);
    }
}
