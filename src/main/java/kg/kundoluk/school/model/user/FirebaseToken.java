package kg.kundoluk.school.model.user;

import kg.kundoluk.school.model.base.TimedEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "m2m_user_firebase_token")
public class FirebaseToken extends TimedEntity {

    private static final long serialVersionUID = 2136284416683111112L;

    @Column(name = "firebase_token")
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
