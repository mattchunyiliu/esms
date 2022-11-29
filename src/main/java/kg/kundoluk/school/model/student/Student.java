package kg.kundoluk.school.model.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.kundoluk.school.components.Selectable;
import kg.kundoluk.school.model.enums.SchoolType;
import kg.kundoluk.school.model.enums.SubscriptionType;
import kg.kundoluk.school.model.instructor.CalendarTopic;
import kg.kundoluk.school.model.user.User;
import kg.kundoluk.school.model.base.PersonEntity;
import kg.kundoluk.school.model.school.SchoolClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student extends PersonEntity implements Selectable {

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "archived")
    private Boolean archived;

    @Column(name = "nationality")
    private String nationality;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private SchoolClass schoolClass;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "subscription_type")
    private SubscriptionType subscriptionType;

    @OneToMany(mappedBy="student")
    private Set<StudentParent> parents;

    @Override
    public String getSelectorId() {
        return null;
    }

    @Override
    public String getSelectorTitle() {
        return String.format("%s %s",getLastName(), getFirstName());
    }
}
