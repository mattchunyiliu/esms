package kg.kundoluk.school.model.school;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kg.kundoluk.school.model.base.TimedEntity;
import kg.kundoluk.school.model.enums.GradeType;
import kg.kundoluk.school.model.enums.SchoolLanguageType;
import kg.kundoluk.school.model.enums.SchoolOrganizationType;
import kg.kundoluk.school.model.enums.SchoolType;
import kg.kundoluk.school.model.location.Rayon;
import kg.kundoluk.school.model.location.Region;
import kg.kundoluk.school.model.location.Town;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"region", "createdAt", "updatedAt"})
@Entity
@Table(name = "school")
public class School extends TimedEntity {

    private static final long serialVersionUID = 7887261690157958128L;

    @Column(nullable = false, name = "school_name")
    private String name;

    @Column(name = "school_code")
    private String code;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "school_type")
    private SchoolType schoolType;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "organization_type")
    private SchoolOrganizationType schoolOrganizationType;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "language_type")
    private SchoolLanguageType schoolLanguageType;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "grade_type")
    private GradeType gradeType;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne
    @JoinColumn(name = "rayon_id")
    private Rayon rayon;

    @ManyToOne
    @JoinColumn(name = "town_id")
    private Town town;

    @Column(name = "study_day")
    private Integer studyDay;

    @Column(name = "is_test")
    private Boolean isTest;

}
