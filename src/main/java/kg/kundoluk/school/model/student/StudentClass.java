package kg.kundoluk.school.model.student;

import kg.kundoluk.school.model.references.ChronicleYear;
import kg.kundoluk.school.model.base.TimedEntity;
import kg.kundoluk.school.model.school.SchoolClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student_class")
public class StudentClass extends TimedEntity {

    private static final long serialVersionUID = 8351818219596357058L;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private SchoolClass schoolClass;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "chronicle_id")
    private ChronicleYear chronicleYear;
}
