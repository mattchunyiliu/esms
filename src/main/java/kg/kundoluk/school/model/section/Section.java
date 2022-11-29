package kg.kundoluk.school.model.section;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kg.kundoluk.school.model.base.TimedEntity;
import kg.kundoluk.school.model.school.School;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"school","createdAt","updatedAt"})
@Entity
public class Section extends TimedEntity {

    private static final long serialVersionUID = 2774540583829015247L;

    @NotNull
    @Column(nullable = false, name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<SectionInstructor> sectionInstructors;
}
