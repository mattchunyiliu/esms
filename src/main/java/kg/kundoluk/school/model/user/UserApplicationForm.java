package kg.kundoluk.school.model.user;

import kg.kundoluk.school.model.references.Role;
import kg.kundoluk.school.model.base.TimedEntity;
import kg.kundoluk.school.model.enums.FormStatusType;
import kg.kundoluk.school.model.school.School;
import kg.kundoluk.school.model.school.SchoolClass;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "user_application_form")
public class UserApplicationForm extends TimedEntity {

    private static final long serialVersionUID = 5442707494741451072L;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "comment")
    private String comment;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "status")
    private FormStatusType statusType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private SchoolClass schoolClass;
}
