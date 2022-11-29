package kg.kundoluk.school.model;

import kg.kundoluk.school.model.base.TimedEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "phone_verification")
public class PhoneVerification extends TimedEntity {

    private static final long serialVersionUID = 2066692772265919683L;
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "expire_at")
    private LocalDateTime expireAt;
}
