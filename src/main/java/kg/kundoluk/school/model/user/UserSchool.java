package kg.kundoluk.school.model.user;

import kg.kundoluk.school.model.base.TimedEntity;
import kg.kundoluk.school.model.school.School;
import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_school")
public class UserSchool extends TimedEntity {

    private static final long serialVersionUID = 6986545237582992566L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "start_year")
    private Integer startYear;

    @Column(name = "end_year")
    private Integer endYear;
}
