package kg.kundoluk.school.model.school;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kg.kundoluk.school.model.base.TimedEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"shift"})
@Entity
@Table(name = "shift_time")
public class ShiftTime extends TimedEntity {

    private static final long serialVersionUID = 5464182694638836098L;
    @NotNull
    @Column(nullable = false, name = "title")
    private String title;

    @Column(name = "start_at")
    private LocalTime startAt;

    @Column(name = "end_at")
    private LocalTime endAt;

    @Column(name = "shift_type")
    private Integer shiftType;

    @ManyToOne
    @JoinColumn(name = "shift_id")
    private Shift shift;
}
