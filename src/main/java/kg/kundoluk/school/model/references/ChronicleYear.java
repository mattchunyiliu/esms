package kg.kundoluk.school.model.references;

import kg.kundoluk.school.components.Selectable;
import kg.kundoluk.school.model.base.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chronicle_year")
public class ChronicleYear extends BaseEntity implements Selectable {

    private static final long serialVersionUID = 4815913848300819769L;

    @Column(name = "start_year")
    private Integer startYear;

    @Column(name = "end_year")
    private Integer endYear;

    @Column(name = "active")
    private Boolean active;

    @Override
    public String getSelectorId() {
        return String.valueOf(getId());
    }

    @Override
    public String getSelectorTitle() {
        return String.format("%d - %d",getStartYear(), getEndYear());
    }
}
