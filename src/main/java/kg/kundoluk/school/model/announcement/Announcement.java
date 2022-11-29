package kg.kundoluk.school.model.announcement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kg.kundoluk.school.model.references.Role;
import kg.kundoluk.school.model.base.TimedEntity;
import kg.kundoluk.school.model.school.School;
import kg.kundoluk.school.model.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "announcement")
public class Announcement extends TimedEntity {

    private static final long serialVersionUID = -6350105175573055876L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "announcement_role",
            joinColumns = {@JoinColumn(name = "announcement_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles;

    @OneToMany(mappedBy = "announcement")
    @JsonIgnoreProperties("announcement")
    private List<AnnouncementClass> classes;
}
