package kg.kundoluk.school.model.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kg.kundoluk.school.model.base.TimedEntity;
import kg.kundoluk.school.model.enums.RayonType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties({"createdAt", "updatedAt", "country", "rayonType"})
@Entity
@Table(name = "ref_location_rayon")
public class Rayon extends TimedEntity {

    @NotNull
    @Column(nullable = false, unique = true)
    private String title;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "rayon_type")
    private RayonType rayonType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;
}
