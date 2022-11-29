package kg.kundoluk.school.model.user;

import kg.kundoluk.school.model.base.TimedEntity;
import kg.kundoluk.school.model.enums.LoginType;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_login_history")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginHistory extends TimedEntity {

    private static final long serialVersionUID = 32041181347789619L;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "action_type")
    private LoginType loginType;
}
