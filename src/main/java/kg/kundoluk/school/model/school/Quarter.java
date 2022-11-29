package kg.kundoluk.school.model.school;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kg.kundoluk.school.model.references.ChronicleYear;
import kg.kundoluk.school.model.base.TimedEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"school"})
@Entity
public class Quarter extends TimedEntity {

    private static final long serialVersionUID = 5635177997487857938L;

    @NotNull
    @Column(nullable = false, name = "title")
    private String quarter;

    @Column(name = "start_at")
    private LocalDate startDate;

    @Column(name = "end_at")
    private LocalDate endDate;

    @Column(name = "active")
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chronicle_id")
    private ChronicleYear chronicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;
}
