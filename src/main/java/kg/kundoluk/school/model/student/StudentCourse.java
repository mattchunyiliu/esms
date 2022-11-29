package kg.kundoluk.school.model.student;

import kg.kundoluk.school.model.references.ChronicleYear;
import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.school.SchoolClass;
import kg.kundoluk.school.model.school.SchoolCourse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "student_course")
public class StudentCourse  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_course_seq_gen")
    @SequenceGenerator(name = "student_course_seq_gen", sequenceName = "student_course_id_seq", allocationSize = 10)
    private Long id;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private SchoolClass schoolClass;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private SchoolCourse schoolCourse;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chronicle_id")
    private ChronicleYear chronicleYear;

    @Column(name = "archived")
    private Boolean archived;
}
