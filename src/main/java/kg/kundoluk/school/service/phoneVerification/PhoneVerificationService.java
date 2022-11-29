package kg.kundoluk.school.service.phoneVerification;

import kg.kundoluk.school.dto.sms.PhoneVerificationDto;
import kg.kundoluk.school.model.PhoneVerification;

import javax.servlet.http.HttpServletRequest;

public interface PhoneVerificationService {
    PhoneVerification create(PhoneVerificationDto phoneVerificationDto);
    PhoneVerification createFromPhone(String phoneNumber, HttpServletRequest request);
    Boolean isValid(String phoneNumber, String code);
}
