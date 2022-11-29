package kg.kundoluk.school.model.location;

import kg.kundoluk.school.model.base.TimedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "ref_location_country")
public class Country extends TimedEntity {

    private static final long serialVersionUID = 5638411259821007451L;
    @NotNull
    @Column(nullable = false, unique = true)
    private String title;
}
