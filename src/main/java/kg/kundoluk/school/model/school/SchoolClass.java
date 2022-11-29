package kg.kundoluk.school.model.school;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kg.kundoluk.school.components.Selectable;
import kg.kundoluk.school.model.references.Language;
import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.base.TimedEntity;
import kg.kundoluk.school.model.enums.ClassType;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"createdAt","updatedAt","school","shift","language","person"})
@Entity
@Table(name = "school_class")
public class SchoolClass extends TimedEntity implements Selectable {

    private static final long serialVersionUID = 8350330970266620124L;

    @NotNull
    @Column(nullable = false, name = "label")
    private String label;

    @NotNull
    @Column(name = "level")
    private Integer level;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "class_type")
    private ClassType classType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_id")
    private Shift shift;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id")
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @Override
    public String getSelectorId() {
        return null;
    }

    @Override
    public String getSelectorTitle() {
        return String.format("%d%s",getLevel(), getLabel());
    }
}
