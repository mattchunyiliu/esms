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
@JsonIgnoreProperties({"createdAt", "updatedAt", "rayon"})
@Entity
@Table(name = "ref_location_town")
public class Town extends TimedEntity {

    private static final long serialVersionUID = 3849609735495687178L;

    @NotNull
    @Column(nullable = false, unique = true)
    private String title;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rayon_id")
    private Rayon rayon;
}
