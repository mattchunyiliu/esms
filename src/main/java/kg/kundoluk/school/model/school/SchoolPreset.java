package kg.kundoluk.school.model.school;

import kg.kundoluk.school.model.references.ChronicleYear;
import kg.kundoluk.school.model.base.TimedEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "school_preset")
public class SchoolPreset extends TimedEntity {

    private static final long serialVersionUID = 7549742959735366278L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chronicle_id")
    private ChronicleYear chronicleYear;

    @Column(name = "is_preset")
    private Boolean isPreset;

    @Column(name = "step_number")
    private Integer stepNumber;

    @Column(name = "is_class_raised")
    private Boolean isClassRaised;
}
