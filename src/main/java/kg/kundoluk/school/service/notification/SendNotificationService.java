package kg.kundoluk.school.service.notification;

import kg.kundoluk.school.dto.assignment.AssignmentCreateRequest;
import kg.kundoluk.school.dto.chat.ChatChannel;
import kg.kundoluk.school.dto.grade.GradeMobileStudentDto;
import kg.kundoluk.school.dto.grade.QuarterGradeCreateRequest;
import kg.kundoluk.school.dto.notification.GradePushRequest;
import kg.kundoluk.school.dto.student.CardAttendanceDto;
import kg.kundoluk.school.dto.student.payment.StudentSubscription;

public interface SendNotificationService {
    void sendGradeNotifications(GradePushRequest gradePushRequest);
    void sendQuarterGradeNotification(QuarterGradeCreateRequest quarterGradeCreateRequest);
    void sendAttendanceNotifications(CardAttendanceDto cardAttendanceDto);
    void sendAssignmentNotifications(AssignmentCreateRequest assignmentCreateRequest);
    void sendSubscriptionNotification(StudentSubscription studentSubscription);
    void sendChatNotification(ChatChannel chatChannel);
}
