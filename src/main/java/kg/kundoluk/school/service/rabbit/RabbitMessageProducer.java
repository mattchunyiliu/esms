package kg.kundoluk.school.service.rabbit;

import kg.kundoluk.school.config.properties.RabbitMQProperties;
import kg.kundoluk.school.dto.assignment.AssignmentCreateRequest;
import kg.kundoluk.school.dto.chat.ChatChannel;
import kg.kundoluk.school.dto.grade.GradeMobileStudentDto;
import kg.kundoluk.school.dto.grade.QuarterGradeCreateRequest;
import kg.kundoluk.school.dto.notification.GradePushRequest;
import kg.kundoluk.school.dto.student.CardAttendanceDto;
import kg.kundoluk.school.dto.student.payment.StudentSubscription;
import kg.kundoluk.school.model.instructor.Assignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMessageProducer {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties rabbitMQProperties;
    private Logger logger = LoggerFactory.getLogger(RabbitMessageProducer.class);

    public RabbitMessageProducer(RabbitTemplate rabbitTemplate, RabbitMQProperties rabbitMQProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQProperties = rabbitMQProperties;
    }

    public void sendPushNotification(GradePushRequest gradePushRequest) {
        rabbitTemplate.convertAndSend(rabbitMQProperties.getExchangeName(), "queue.grade", gradePushRequest);
    }

    public void sendCardAttendancePushNotification(CardAttendanceDto cardAttendanceDto) {
        rabbitTemplate.convertAndSend(rabbitMQProperties.getExchangeName(), "queue.card.attendance", cardAttendanceDto);
    }

    public void sendAssignmentNotification(AssignmentCreateRequest assignmentCreateRequest) {
        rabbitTemplate.convertAndSend(rabbitMQProperties.getExchangeName(), "queue.assignment", assignmentCreateRequest);
    }

    public void sendQuarterGradeNotification(QuarterGradeCreateRequest quarterGradeCreateRequest) {
        rabbitTemplate.convertAndSend(rabbitMQProperties.getExchangeName(), "queue.quarter.grade", quarterGradeCreateRequest);
    }

    public void sendSubscriptionNotification(StudentSubscription studentSubscription) {
        rabbitTemplate.convertAndSend(rabbitMQProperties.getExchangeName(), "queue.subscription", studentSubscription);
    }

    public void sendChatNotification(ChatChannel chatChannel) {
        rabbitTemplate.convertAndSend(rabbitMQProperties.getExchangeName(), "queue.chat", chatChannel);
    }
}
