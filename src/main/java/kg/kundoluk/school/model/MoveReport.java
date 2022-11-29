package kg.kundoluk.school.model;

import kg.kundoluk.school.model.base.TimedEntity;
import kg.kundoluk.school.model.enums.Gender;
import kg.kundoluk.school.model.enums.MoveActionType;
import kg.kundoluk.school.model.enums.MoveType;
import kg.kundoluk.school.model.school.Quarter;
import kg.kundoluk.school.model.school.SchoolClass;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student_move_statistics")
public class MoveReport extends TimedEntity {

    private static final long serialVersionUID = 2235947428130733920L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quarter_id")
    private Quarter quarter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private SchoolClass schoolClass;

    /*@Enumerated(EnumType.ORDINAL)
    @Column(name = "move_type")
    private MoveType moveType;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "action_type")
    private MoveActionType moveActionType;*/

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "count")
    private Integer count;

    @Column(name = "initial_count")
    private Integer initialCount;

    @Column(name = "final_count")
    private Integer finalCount;

    @Column(name = "dep_country_count")
    private Integer depCountryCount;

    @Column(name = "dep_region_count")
    private Integer depRegionCount;

    @Column(name = "dep_rayon_count")
    private Integer depRayonCount;

    @Column(name = "arr_country_count")
    private Integer arrCountryCount;

    @Column(name = "arr_region_count")
    private Integer arrRegionCount;

    @Column(name = "arr_rayon_count")
    private Integer arrRayonCount;
}
