package kg.kundoluk.school.repository;

import kg.kundoluk.school.model.PhoneVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface PhoneVerificationRepository extends JpaRepository<PhoneVerification, Long> {

    @Query("SELECT CASE WHEN COUNT(pv) > 0 THEN true ELSE false END FROM PhoneVerification pv WHERE pv.phoneNumber = ?1 and pv.verificationCode = ?2 and pv.expireAt >= ?3")
    Boolean existsByPhoneNumberAndVerificationCodeAndExpireAtBefore(String phoneNumber, String verificationCode, LocalDateTime now);
}
