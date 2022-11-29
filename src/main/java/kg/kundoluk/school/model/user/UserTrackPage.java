package kg.kundoluk.school.model.user;


import kg.kundoluk.school.model.base.TimedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "user_track_pages")
public class UserTrackPage extends TimedEntity {

    private static final long serialVersionUID = 8112525618924989635L;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String ipAddress;

    private String page;

    private Integer device;
}
