package kg.kundoluk.school.model.school;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kg.kundoluk.school.model.references.Course;
import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.base.BaseEntity;
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
@JsonIgnoreProperties({"school"})
@Entity
@Table(name = "school_course")
public class SchoolCourse extends BaseEntity {

    private static final long serialVersionUID = 407784853663140700L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "archived")
    private Boolean archived;

    @ManyToMany(mappedBy="courses")
    private Set<Person> personSet = new HashSet<>();
}
