package kg.kundoluk.school.model.student;

import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.base.TimedEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "student_parent")
public class StudentParent extends TimedEntity {

    private static final long serialVersionUID = 8493952691289964156L;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Person parent;

    @Column(name = "parental_type")
    private Integer parentalType;
}
