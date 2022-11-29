package kg.kundoluk.school.controller.mobile.user;

import kg.kundoluk.school.constants.Constants;
import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.calendar.CalendarTopicCreateRequest;
import kg.kundoluk.school.dto.notification.PushNotificationRequest;
import kg.kundoluk.school.dto.user.FirebaseTokenRequest;
import kg.kundoluk.school.dto.user.UserLanguageUpdate;
import kg.kundoluk.school.service.user.FirebaseTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/mobile/v1/firebase-token/")
public class FirebaseTokenController {
    private final FirebaseTokenService firebaseTokenService;

    public FirebaseTokenController(FirebaseTokenService firebaseTokenService) {
        this.firebaseTokenService = firebaseTokenService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody FirebaseTokenRequest firebaseTokenRequest ) {
        firebaseTokenService.create(firebaseTokenRequest);
        return new ResponseEntity<>(new ApiResponse(true, Constants.CREATED), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping("/create-notification")
    public ResponseEntity<?> createNotification(@RequestBody PushNotificationRequest pushNotificationRequest) {
        firebaseTokenService.createNotification(pushNotificationRequest);
        return new ResponseEntity<>(new ApiResponse(true, Constants.CREATED), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/change-language")
    public ResponseEntity<?> changeLanguage(@RequestBody UserLanguageUpdate userLanguageUpdate) {
        firebaseTokenService.updateLanguage(userLanguageUpdate);
        return new ResponseEntity<>(new ApiResponse(true, Constants.UPDATED), HttpStatus.OK);
    }

    @DeleteMapping
    public Boolean delete(
            @RequestParam("token") String token
    ) {
        firebaseTokenService.delete(token);
        return true;
    }
}
