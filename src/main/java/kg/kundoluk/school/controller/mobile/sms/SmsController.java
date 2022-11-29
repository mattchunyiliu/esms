package kg.kundoluk.school.controller.mobile.sms;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.sms.PhoneVerificationDto;
import kg.kundoluk.school.model.PhoneVerification;
import kg.kundoluk.school.service.phoneVerification.PhoneVerificationService;
import kg.kundoluk.school.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/v1/sms/")
public class SmsController {

    private final UserService userService;
    private final PhoneVerificationService phoneVerificationService;

    public SmsController(UserService userService, PhoneVerificationService phoneVerificationService) {
        this.userService = userService;
        this.phoneVerificationService = phoneVerificationService;
    }

    @GetMapping("/code")
    public ResponseEntity<?> createSmsCode(HttpServletRequest request, String mobile) {
        if (!userService.existByPhone(mobile.trim())){
            return new ResponseEntity<>(new ApiResponse(false, "USER_NOT_FOUND"), HttpStatus.NOT_FOUND);
        }
        PhoneVerification phoneVerification = phoneVerificationService.createFromPhone(mobile, request);
        return new ResponseEntity<>(new ApiResponse(true, phoneVerification.getVerificationCode()), HttpStatus.OK);
    }

    @GetMapping("/verification")
    public ResponseEntity<?> createVerificationCode(String mobile) {
        PhoneVerification phoneVerification = phoneVerificationService.create(new PhoneVerificationDto(mobile));
        return new ResponseEntity<>(new ApiResponse(true, phoneVerification.getVerificationCode()), HttpStatus.OK);
    }

    @GetMapping("/validation")
    public Boolean validate(String mobile, String code) {
        return phoneVerificationService.isValid(mobile, code);
    }
}
