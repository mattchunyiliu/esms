package kg.kundoluk.school.service.user.impl;

import kg.kundoluk.school.dto.notification.PushNotificationRequest;
import kg.kundoluk.school.dto.projection.FirebaseTokenDTO;
import kg.kundoluk.school.dto.projection.UserSchoolDTO;
import kg.kundoluk.school.dto.user.FirebaseTokenRequest;
import kg.kundoluk.school.dto.user.UserLanguageUpdate;
import kg.kundoluk.school.model.user.FirebaseToken;
import kg.kundoluk.school.repository.FirebaseTokenRepository;
import kg.kundoluk.school.repository.UserRepository;
import kg.kundoluk.school.service.notification.PushNotificationService;
import kg.kundoluk.school.service.user.FirebaseTokenService;
import kg.kundoluk.school.service.user.UserSchoolService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FirebaseTokenServiceImpl implements FirebaseTokenService {
    private final FirebaseTokenRepository firebaseTokenRepository;
    private final UserRepository userRepository;
    private final PushNotificationService pushNotificationService;
    private final UserSchoolService userSchoolService;

    public FirebaseTokenServiceImpl(FirebaseTokenRepository firebaseTokenRepository, UserRepository userRepository, PushNotificationService pushNotificationService, UserSchoolService userSchoolService) {
        this.firebaseTokenRepository = firebaseTokenRepository;
        this.userRepository = userRepository;
        this.pushNotificationService = pushNotificationService;
        this.userSchoolService = userSchoolService;
    }

    @Override
    public FirebaseToken create(FirebaseTokenRequest firebaseTokenRequest) {
        FirebaseToken firebaseToken = firebaseTokenRepository.findFirstByToken(firebaseTokenRequest.getToken());
        if (firebaseToken == null){
            FirebaseToken newToken = new FirebaseToken();
            newToken.setToken(firebaseTokenRequest.getToken());
            newToken.setUser(userRepository.getOne(firebaseTokenRequest.getUserId()));
            firebaseToken = firebaseTokenRepository.save(newToken);
        }

        List<String> tokens = new ArrayList<>();
        tokens.add(firebaseTokenRequest.getToken());

        // SUBSCRIBE TO KUNDOLUK NEWS TOPIC
        pushNotificationService.subscribe(tokens, "kundoluk_news");

        // SUBSCRIBE TO SCHOOL NEWS TOPIC
        List<UserSchoolDTO> userSchoolDTOS = userSchoolService.getUserSchool(firebaseTokenRequest.getUserId());
        for (UserSchoolDTO userSchoolDTO: userSchoolDTOS){
            String topic = String.format("%s%d","school_news_", userSchoolDTO.getSchoolId());
            pushNotificationService.subscribe(tokens, topic);
        }

        return firebaseToken;
    }

    @Override
    public void createNotification(PushNotificationRequest pushNotificationRequest) {
        pushNotificationService.sendPushNotificationWithoutData(pushNotificationRequest);
    }

    @Override
    public void delete(String token) {
        firebaseTokenRepository.deleteByToken(token);
    }

    @Override
    public void updateLanguage(UserLanguageUpdate userLanguageUpdate) {
        userRepository.updateLanguage(userLanguageUpdate.getUserId(), userLanguageUpdate.getLanguageId());
    }

    @Override
    public List<FirebaseTokenDTO> getUserTokens(Long userId) {
        return firebaseTokenRepository.findAllByUserId(userId);
    }
}
