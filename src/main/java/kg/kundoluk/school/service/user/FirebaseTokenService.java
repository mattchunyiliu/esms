package kg.kundoluk.school.service.user;

import kg.kundoluk.school.dto.notification.PushNotificationRequest;
import kg.kundoluk.school.dto.projection.FirebaseTokenDTO;
import kg.kundoluk.school.dto.user.FirebaseTokenRequest;
import kg.kundoluk.school.dto.user.UserLanguageUpdate;
import kg.kundoluk.school.model.user.FirebaseToken;
import kg.kundoluk.school.model.user.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface FirebaseTokenService {
    FirebaseToken create(FirebaseTokenRequest firebaseTokenRequest);
    void createNotification(PushNotificationRequest pushNotificationRequest);
    void delete(String token);
    void updateLanguage(UserLanguageUpdate userLanguageUpdate);
    List<FirebaseTokenDTO> getUserTokens(Long userId);
}
