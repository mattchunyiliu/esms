package kg.kundoluk.school.service.notification;

import kg.kundoluk.school.dto.notification.NotificationCreateRequest;
import kg.kundoluk.school.dto.notification.PushNotificationRequest;
import kg.kundoluk.school.dto.variables.NotificationType;
import kg.kundoluk.school.service.firebase.FCMService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Service
public class PushNotificationService {

    private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);
    private final FCMService fcmService;

    public PushNotificationService(FCMService fcmService) {
        this.fcmService = fcmService;
    }

    public void subscribe(List<String> tokens, String topic) {
        try {
            fcmService.subscribeTopic(tokens, topic);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendPushNotification(PushNotificationRequest request, NotificationCreateRequest notificationCreateRequest) {
        try {
            fcmService.sendMessage(getSamplePayloadData(notificationCreateRequest), request);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendPushNotificationWithoutData(PushNotificationRequest request) {
        try {
            fcmService.sendMessageWithoutData(request);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendPushNotificationToToken(PushNotificationRequest request, NotificationCreateRequest notificationCreateRequest) {
        try {
            fcmService.sendMessageToToken(getSamplePayloadData(notificationCreateRequest), request);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }

    private Map<String, String> getSamplePayloadData(NotificationCreateRequest notificationCreateRequest) {

        Map<String, String> pushData = toPushData(notificationCreateRequest);

        if (notificationCreateRequest.getNotificationType().equals(NotificationType.CHAT)){

            pushData.put("actorId", String.valueOf(notificationCreateRequest.getSenderId()));
            pushData.put("actorName", notificationCreateRequest.getSenderTitle());
            pushData.put("recipientId", String.valueOf(notificationCreateRequest.getRecipientId()));

        } else {
            pushData.put("contents", notificationCreateRequest.getContents());
        }

        return pushData;
    }

    private Map<String, String> toPushData(NotificationCreateRequest notificationCreateRequest){

        Map<String, String> pushData = new HashMap<>();

        Random random = new Random();
        pushData.put("messageId", String.valueOf(Math.abs(random.nextInt())));
        pushData.put("text",  LocalDateTime.now().toString());
        pushData.put("title", notificationCreateRequest.getTitle());
        pushData.put("url", notificationCreateRequest.getUrl());
        pushData.put("click_action", "FLUTTER_NOTIFICATION_CLICK");
        pushData.put("status", notificationCreateRequest.getStatus().toString());
        pushData.put("notificationType", notificationCreateRequest.getNotificationType().toString());

        return pushData;
    }
}
