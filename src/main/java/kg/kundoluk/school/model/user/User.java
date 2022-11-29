package kg.kundoluk.school.model.user;


import kg.kundoluk.school.components.Selectable;
import kg.kundoluk.school.model.references.Language;
import kg.kundoluk.school.model.references.Role;
import kg.kundoluk.school.model.base.TimedEntity;
import kg.kundoluk.school.model.location.Rayon;
import kg.kundoluk.school.model.school.School;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "users")
public class User extends TimedEntity implements Selectable {

    private static final long serialVersionUID = -5451783785123301445L;

    @Column(name = "username")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(columnDefinition = "boolean default true")
    private boolean enabled;

    @Column(name = "phone_number")
    private String phone;

    @Column(columnDefinition = "boolean default false")
    private boolean phoneVerified;

    @Column(name = "avatar")
    private String avatar;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_school",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "school_id", referencedColumnName = "id")})
    private Set<School> schools;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FirebaseToken> firebaseTokens;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id")
    private Language language;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_rayon",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "rayon_id", referencedColumnName = "id")})
    private Set<Rayon> rayons;

    @Transient
    private Set<GrantedAuthority> sAuthorities = new HashSet<>();

    public User() {}

    public User(String username, String firstName, String lastName, String email) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

    }

    @Override
    public String getSelectorId() {
        return null;
    }

    @Override
    public String getSelectorTitle() {
        return String.format("%s %s", lastName, firstName);
    }
}
