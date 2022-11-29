package kg.kundoluk.school.model.audit;

import kg.kundoluk.school.model.user.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "revision")
@RevisionEntity(AppRevisionListener.class)
public class RevEntity {

    @Id
    @RevisionNumber
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @RevisionTimestamp
    private long timestamp;

    @Column(name = "auditor_id")
    private Long auditorId;

    public RevEntity() {
    }
}
