package kg.kundoluk.school.service.rabbit;

import kg.kundoluk.school.dto.assignment.AssignmentCreateRequest;
import kg.kundoluk.school.dto.chat.ChatChannel;
import kg.kundoluk.school.dto.grade.GradeMobileStudentDto;
import kg.kundoluk.school.dto.grade.QuarterGradeCreateRequest;
import kg.kundoluk.school.dto.notification.GradePushRequest;
import kg.kundoluk.school.dto.student.CardAttendanceDto;
import kg.kundoluk.school.dto.student.payment.StudentSubscription;
import kg.kundoluk.school.service.notification.SendNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMessageConsumer {

    private final SendNotificationService sendNotificationService;
    private final Logger logger = LoggerFactory.getLogger(RabbitMessageConsumer.class);

    public RabbitMessageConsumer(SendNotificationService sendNotificationService) {
        this.sendNotificationService = sendNotificationService;
    }

    @RabbitListener(queues = "gradeQueue")
    public void receivedGradeMessage(GradePushRequest gradePushRequest) {
        logger.info("RabbitMessageConsumer Grade");
        sendNotificationService.sendGradeNotifications(gradePushRequest);
    }

    @RabbitListener(queues = "quarterGradeQueue")
    public void receivedQuarterGradeMessage(QuarterGradeCreateRequest quarterGradeCreateRequest) {
        logger.info("RabbitMessageConsumer Quarter Grade");
        sendNotificationService.sendQuarterGradeNotification(quarterGradeCreateRequest);
    }

    @RabbitListener(queues = "attendanceQueue")
    public void receivedAttendanceMessage(CardAttendanceDto cardAttendanceDto) {
        logger.info("RabbitMessageConsumer Attendance");
        sendNotificationService.sendAttendanceNotifications(cardAttendanceDto);
    }

    @RabbitListener(queues = "assignmentQueue")
    public void receivedAssignmentMessage(AssignmentCreateRequest assignmentCreateRequest) {
        logger.info("RabbitMessageConsumer Assignment");
        sendNotificationService.sendAssignmentNotifications(assignmentCreateRequest);
    }

    @RabbitListener(queues = "subscriptionQueue")
    public void receivedGradeMessage(StudentSubscription studentSubscription) {
        sendNotificationService.sendSubscriptionNotification(studentSubscription);
    }

    @RabbitListener(queues = "chatQueue")
    public void receivedChatMessage(ChatChannel chatChannel) {
        sendNotificationService.sendChatNotification(chatChannel);
    }
}
