package kg.kundoluk.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.kundoluk.school.components.Selectable;
import kg.kundoluk.school.model.base.PersonEntity;
import kg.kundoluk.school.model.school.SchoolCourse;
import kg.kundoluk.school.model.user.User;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "person")
public class Person extends PersonEntity implements Selectable {

    private static final long serialVersionUID = 7986882120422287324L;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "archived")
    private Boolean archived;

    @Column(name = "job")
    private String job;

    @Column(name = "job_place")
    private String jobPlace;

    @Column(name = "phone_number")
    private String phone;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "m2m_instructor_course",
            joinColumns = {@JoinColumn(name = "instructor_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")})
    private Set<SchoolCourse> courses = new HashSet<>();

    public void addCourse(SchoolCourse course) {
        courses.add(course);
        //course.getPersonSet().add(this);
    }

    public void addCourses(Set<SchoolCourse> courseList) {
        courses.addAll(courseList);
    }

    public void removeCourse(SchoolCourse course) {
        courses.remove(course);
        //course.getPersonSet().remove(this);
    }

    @Override
    public String getSelectorId() {
        return null;
    }

    @Override
    public String getSelectorTitle() {
        if (StringUtils.isEmpty(this.getMiddleName()) || this.getMiddleName().equals("NULL"))
            return String.format("%s %s", this.getLastName(), this.getFirstName());
        return String.format("%s %s %s", this.getLastName(), this.getFirstName(), this.getMiddleName());
    }

    @Override
    public String toString() {
        return "Person{" +
                "first name ='" + getFirstName() + '\'' +
                "last name ='" + getLastName() + '\'' +
                '}';
    }
}
