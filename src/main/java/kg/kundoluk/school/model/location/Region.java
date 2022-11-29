package kg.kundoluk.school.model.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kg.kundoluk.school.model.base.TimedEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties({"createdAt", "updatedAt", "country"})
@Entity
@Table(name = "ref_location_region")
public class Region extends TimedEntity {

    private static final long serialVersionUID = -8029039258038162564L;
    @NotNull
    @Column(nullable = false, unique = true)
    private String title;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
}
