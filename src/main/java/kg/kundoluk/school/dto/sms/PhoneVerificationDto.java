package kg.kundoluk.school.dto.sms;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PhoneVerificationDto {
    private String phoneNumber;
    private String verificationCode;
    private LocalDateTime expireAt;

    public PhoneVerificationDto(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
