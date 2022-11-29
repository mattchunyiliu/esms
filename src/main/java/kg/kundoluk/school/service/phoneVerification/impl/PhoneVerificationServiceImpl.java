package kg.kundoluk.school.service.phoneVerification.impl;

import kg.kundoluk.school.dto.sms.PhoneVerificationDto;
import kg.kundoluk.school.dto.sms.SmsCode;
import kg.kundoluk.school.model.PhoneVerification;
import kg.kundoluk.school.repository.PhoneVerificationRepository;
import kg.kundoluk.school.service.phoneVerification.PhoneVerificationService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
public class PhoneVerificationServiceImpl implements PhoneVerificationService {
    private final PhoneVerificationRepository phoneVerificationRepository;

    public PhoneVerificationServiceImpl(PhoneVerificationRepository phoneVerificationRepository) {
        this.phoneVerificationRepository = phoneVerificationRepository;
    }

    @Override
    public PhoneVerification create(PhoneVerificationDto phoneVerificationDto) {
        SmsCode smsCode = createSMSCode();

        return phoneVerificationRepository.save(
                new PhoneVerification()
                        .setVerificationCode(smsCode.getCode())
                        .setPhoneNumber(phoneVerificationDto.getPhoneNumber())
                        .setExpireAt(smsCode.getExpireTime())
        );
    }

    @Override
    public PhoneVerification createFromPhone(String phoneNumber, HttpServletRequest request) {

        SmsCode smsCode = createSMSCode();

        PhoneVerification phoneVerification = new PhoneVerification();
        phoneVerification.setPhoneNumber(phoneNumber);
        phoneVerification.setVerificationCode(smsCode.getCode());
        phoneVerification.setExpireAt(smsCode.getExpireTime());

        return phoneVerificationRepository.save(phoneVerification);
    }

    @Override
    public Boolean isValid(String phoneNumber, String code) {
        return phoneVerificationRepository.existsByPhoneNumberAndVerificationCodeAndExpireAtBefore(phoneNumber, code, LocalDateTime.now());
    }

    private SmsCode createSMSCode() {
        String code = RandomStringUtils.randomNumeric(6);
        return new SmsCode(code, 3600);
    }
}
