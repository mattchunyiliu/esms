package kg.kundoluk.school.service.notification.impl;

import kg.kundoluk.school.dto.assignment.AssignmentCreateRequest;
import kg.kundoluk.school.dto.chat.ChatChannel;
import kg.kundoluk.school.dto.grade.QuarterGradeCreateRequest;
import kg.kundoluk.school.dto.notification.GradePushRequest;
import kg.kundoluk.school.dto.notification.NotificationCreateRequest;
import kg.kundoluk.school.dto.notification.PushNotificationRequest;
import kg.kundoluk.school.dto.projection.FirebaseTokenDTO;
import kg.kundoluk.school.dto.projection.StudentViewDTO;
import kg.kundoluk.school.dto.student.CardAttendanceDto;
import kg.kundoluk.school.dto.student.payment.StudentSubscription;
import kg.kundoluk.school.dto.variables.NotificationStatusType;
import kg.kundoluk.school.dto.variables.NotificationType;
import kg.kundoluk.school.model.chat.UserChatMessage;
import kg.kundoluk.school.model.references.Course;
import kg.kundoluk.school.model.enums.Gender;
import kg.kundoluk.school.model.enums.GradeMarkType;
import kg.kundoluk.school.model.enums.QuarterGradeMarkType;
import kg.kundoluk.school.model.enums.SubscriptionType;
import kg.kundoluk.school.model.instructor.CalendarTopic;
import kg.kundoluk.school.model.school.Quarter;
import kg.kundoluk.school.model.student.Student;
import kg.kundoluk.school.model.student.StudentCourse;
import kg.kundoluk.school.repository.UserChatMessageRepository;
import kg.kundoluk.school.repository.UserChatRoomRepository;
import kg.kundoluk.school.service.calendar.CalendarTopicService;
import kg.kundoluk.school.service.chat.UserChatMessageService;
import kg.kundoluk.school.service.notification.NotificationService;
import kg.kundoluk.school.service.notification.PushNotificationService;
import kg.kundoluk.school.service.notification.SendNotificationService;
import kg.kundoluk.school.service.school.QuarterService;
import kg.kundoluk.school.service.student.StudentCourseService;
import kg.kundoluk.school.service.student.StudentParentService;
import kg.kundoluk.school.service.student.StudentService;
import kg.kundoluk.school.service.user.FirebaseTokenService;
import kg.kundoluk.school.utils.TimeHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class SendNotificationServiceImpl implements SendNotificationService {

    private final StudentCourseService studentCourseService;
    private final PushNotificationService pushNotificationService;
    private final StudentParentService studentParentService;
    private final StudentService studentService;
    private final CalendarTopicService calendarTopicService;
    private final NotificationService notificationService;
    private final QuarterService quarterService;
    private final UserChatMessageRepository userChatMessageRepository;
    private final UserChatRoomRepository userChatRoomRepository;
    private final FirebaseTokenService firebaseTokenService;
    private static final Logger logger = LoggerFactory.getLogger(SendNotificationServiceImpl.class);
    private final MessageSource messageSource;
    public SendNotificationServiceImpl(StudentCourseService studentCourseService, PushNotificationService pushNotificationService, StudentParentService studentParentService, StudentService studentService, CalendarTopicService calendarTopicService, NotificationService notificationService, QuarterService quarterService, UserChatMessageRepository userChatMessageRepository, UserChatRoomRepository userChatRoomRepository, FirebaseTokenService firebaseTokenService, MessageSource messageSource) {
        this.studentCourseService = studentCourseService;
        this.pushNotificationService = pushNotificationService;
        this.studentParentService = studentParentService;
        this.studentService = studentService;
        this.calendarTopicService = calendarTopicService;
        this.notificationService = notificationService;
        this.quarterService = quarterService;
        this.userChatMessageRepository = userChatMessageRepository;
        this.userChatRoomRepository = userChatRoomRepository;
        this.firebaseTokenService = firebaseTokenService;
        this.messageSource = messageSource;
    }

    @Override
    public void sendGradeNotifications(GradePushRequest gradePushRequest) {

        StudentCourse studentCourse = studentCourseService.findById(gradePushRequest.getStudentCourseId());

        NotificationCreateRequest notificationCreateRequest = new NotificationCreateRequest(NotificationType.GRADE, NotificationStatusType.UNREAD);
        notificationCreateRequest.setUrl("grade");
        notificationCreateRequest.setSenderId(gradePushRequest.getUserId());

        List<String> tokens = new ArrayList<>();
        List<Long> userIds = new ArrayList<>();

        Student student = studentCourse.getStudent();
        SubscriptionType subscriptionType = getSubscription(student.getSubscriptionType());

        if (subscriptionType.equals(SubscriptionType.PREMIUM)) {
            notificationCreateRequest.setTitle(studentCourse.getStudent().getSelectorTitle());

            List<FirebaseTokenDTO> parentTokens = studentParentService.findParentTokensByStudent(student.getId());

            for (FirebaseTokenDTO firebaseTokenDTO : parentTokens) {
                Locale locale = detectLanguageCode(firebaseTokenDTO.getLanguageCode());
                if (gradePushRequest.getGradeMarkType().equals(GradeMarkType.GRADE)) { // GRADE NOTIFICATION
                    String mark = "";
                    if (!StringUtils.isEmpty(gradePushRequest.getMark())) {
                        mark = messageSource.getMessage("mark", null, locale) + " : " + gradePushRequest.getMark();
                    }
                    notificationCreateRequest.setContents(getLocalizedCourseTitle(studentCourse.getSchoolCourse().getCourse(), firebaseTokenDTO.getLanguageCode()) + "\n" +
                            mark + "\n" + messageSource.getMessage("date", null, locale) + " : " + TimeHelper.DATE_FORMATTER.format(LocalDate.now()));
                } else { // ATTENDANCE NOTIFICATION
                    String attendanceReason = gradePushRequest.getMark().equalsIgnoreCase("92") ? "valid-attendance" : "invalid-attendance";
                    notificationCreateRequest.setContents(getLocalizedCourseTitle(studentCourse.getSchoolCourse().getCourse(), firebaseTokenDTO.getLanguageCode()) + "\n" +
                            messageSource.getMessage("no-attendance", null, locale) + ":" +
                            messageSource.getMessage(attendanceReason, null, locale));
                }

                notificationCreateRequest.setRecipientId(firebaseTokenDTO.getUserId());

                notifyUser(notificationCreateRequest, tokens, userIds, firebaseTokenDTO);
            }
        }
    }

    @Override
    public void sendQuarterGradeNotification(QuarterGradeCreateRequest quarterGradeCreateRequest) {
        StudentCourse studentCourse = studentCourseService.findById(quarterGradeCreateRequest.getStudentCourseId());

        NotificationCreateRequest notificationCreateRequest = new NotificationCreateRequest(NotificationType.QUARTER_GRADE, NotificationStatusType.UNREAD);
        notificationCreateRequest.setUrl("quarter.grade");

        List<String> tokens = new ArrayList<>();
        List<Long> userIds = new ArrayList<>();

        Student student = studentCourse.getStudent();

        SubscriptionType subscriptionType = getSubscription(student.getSubscriptionType());

        if (subscriptionType.equals(SubscriptionType.PREMIUM)) {
            Quarter quarter = quarterService.get(quarterGradeCreateRequest.getQuarterId());

            notificationCreateRequest.setTitle(student.getSelectorTitle());

            List<FirebaseTokenDTO> parentTokens = studentParentService.findParentTokensByStudent(student.getId());

            for (FirebaseTokenDTO firebaseTokenDTO : parentTokens) {
                Locale locale = detectLanguageCode(firebaseTokenDTO.getLanguageCode());

                String mark = "";
                if (!StringUtils.isEmpty(quarterGradeCreateRequest.getMark())) {
                    mark = messageSource.getMessage("mark", null, locale) + " : " + quarterGradeCreateRequest.getMark();
                }
                notificationCreateRequest.setContents(getLocalizedCourseTitle(studentCourse.getSchoolCourse().getCourse(), firebaseTokenDTO.getLanguageCode()) + "\n" +
                        mark + "\n" + getQuarterType(quarterGradeCreateRequest.getGradeMarkType(), locale, quarter));


                notificationCreateRequest.setRecipientId(firebaseTokenDTO.getUserId());

                notifyUser(notificationCreateRequest, tokens, userIds, firebaseTokenDTO);
            }
        }
    }


    @Override
    public void sendAttendanceNotifications(CardAttendanceDto cardAttendanceDto) {

        StudentViewDTO student = studentService.findViewById(cardAttendanceDto.getStudentId());
        if (student.getSubscriptionType().equals(SubscriptionType.FREE.ordinal()))
            return;

        List<FirebaseTokenDTO> parentTokens = studentParentService.findParentTokensByStudent(cardAttendanceDto.getStudentId());

        NotificationCreateRequest notificationCreateRequest = new NotificationCreateRequest(NotificationType.ATTENDANCE, NotificationStatusType.UNREAD);
        notificationCreateRequest.setUrl("grade");
        notificationCreateRequest.setTitle(String.format("%s %s",student.getLastName(), student.getFirstName()));

        String message;

        List<String> tokens = new ArrayList<>();
        List<Long> userIds = new ArrayList<>();
        for (FirebaseTokenDTO firebaseTokenDTO: parentTokens) {
            if (student.getGender().equals(Gender.MALE.ordinal())) {
                if (cardAttendanceDto.getAttendanceType() == 1) {
                    message = messageSource.getMessage("enter.school.boy", null, detectLanguageCode(firebaseTokenDTO.getLanguageCode()));
                } else {
                    message = messageSource.getMessage("left.school.boy", null, detectLanguageCode(firebaseTokenDTO.getLanguageCode()));
                }
            } else {
                if (cardAttendanceDto.getAttendanceType() == 1) {
                    message = messageSource.getMessage("enter.school.girl", null, detectLanguageCode(firebaseTokenDTO.getLanguageCode()));
                } else {
                    message = messageSource.getMessage("left.school.girl", null, detectLanguageCode(firebaseTokenDTO.getLanguageCode()));
                }
            }
            notificationCreateRequest.setContents(message);
            notificationCreateRequest.setRecipientId(firebaseTokenDTO.getUserId());

            notifyUser(notificationCreateRequest, tokens, userIds, firebaseTokenDTO);
        }
    }

    @Override
    public void sendAssignmentNotifications(AssignmentCreateRequest assignmentCreateRequest) {

        CalendarTopic calendarTopic = calendarTopicService.findById(assignmentCreateRequest.getCalendarTopicId());

        NotificationCreateRequest notificationCreateRequest = new NotificationCreateRequest(NotificationType.ASSIGNMENT, NotificationStatusType.UNREAD);
        notificationCreateRequest.setUrl("assignment");
        String title = StringUtils.isEmpty(assignmentCreateRequest.getTitle())?calendarTopic.getTitle(): assignmentCreateRequest.getTitle();
        notificationCreateRequest.setTitle(title);

        List<FirebaseTokenDTO> parentTokens = studentParentService.findParentTokensByClass(calendarTopic.getCalendarPlan().getSchoolClass().getId());
        String message;

        List<String> tokens = new ArrayList<>();
        List<Long> userIds = new ArrayList<>();
        for (FirebaseTokenDTO firebaseTokenDTO: parentTokens) {
            Locale locale = detectLanguageCode(firebaseTokenDTO.getLanguageCode());
            message = messageSource.getMessage("homework", null, locale) + messageSource.getMessage("date", null, locale) + " : " + assignmentCreateRequest.getDeadlineAt();

            notificationCreateRequest.setContents(message);
            notifyUser(notificationCreateRequest, tokens, userIds, firebaseTokenDTO);
        }
    }

    @Override
    public void sendSubscriptionNotification(StudentSubscription studentSubscription) {
        NotificationCreateRequest notificationCreateRequest = new NotificationCreateRequest(NotificationType.SUBSCRIPTION, NotificationStatusType.UNREAD);
        notificationCreateRequest.setUrl("subscription");

        SubscriptionType subscriptionType = getSubscription(studentSubscription.getSubscriptionType());

        if (subscriptionType.equals(SubscriptionType.PREMIUM)) {
            for (Long studentId : studentSubscription.getStudentIds()) {
                List<FirebaseTokenDTO> parentTokens = studentParentService.findParentTokensByStudent(studentId);
                for (FirebaseTokenDTO firebaseTokenDTO : parentTokens) {
                    Locale locale = detectLanguageCode(firebaseTokenDTO.getLanguageCode());
                    notificationCreateRequest.setTitle(messageSource.getMessage("welcome.subscription", null, locale));
                    notificationCreateRequest.setContents(messageSource.getMessage("welcome.subscription.content", null, locale));
                    notificationCreateRequest.setRecipientId(firebaseTokenDTO.getUserId());
                    notifyToken(firebaseTokenDTO, notificationCreateRequest);
                }
            }
        }
    }

    @Override
    public void sendChatNotification(ChatChannel chatChannel) {
        Integer userChatMessageStatus = userChatMessageRepository.getUserChatMessageStatus(chatChannel.getChatId());

        if (userChatMessageStatus == 1 && chatChannel.getRecipientId() != null) { // UNREAD
            List<FirebaseTokenDTO> userTokens = firebaseTokenService.getUserTokens(chatChannel.getRecipientId());

            NotificationCreateRequest notificationCreateRequest = new NotificationCreateRequest(NotificationType.CHAT, NotificationStatusType.UNREAD);
            notificationCreateRequest.setUrl(chatChannel.getChannelId());

            List<String> tokens = new ArrayList<>();
            for (FirebaseTokenDTO firebaseTokenDTO : userTokens) {
                notificationCreateRequest.setTitle(chatChannel.getSenderTitle());
                notificationCreateRequest.setContents(chatChannel.getContent());
                notificationCreateRequest.setRecipientId(chatChannel.getRecipientId());
                notificationCreateRequest.setSenderId(chatChannel.getSenderId());
                notificationCreateRequest.setSenderTitle(chatChannel.getSenderTitle());

                if (tokens.stream().noneMatch(s->!StringUtils.isEmpty(firebaseTokenDTO.getToken()) && s.equalsIgnoreCase(firebaseTokenDTO.getToken())))
                    notifyToken(firebaseTokenDTO, notificationCreateRequest);
                if (!StringUtils.isEmpty(firebaseTokenDTO.getToken()))
                    tokens.add(firebaseTokenDTO.getToken());
            }
        }

    }

    private SubscriptionType getSubscription(SubscriptionType subscriptionType){
        return subscriptionType == null || subscriptionType == SubscriptionType.FREE ? SubscriptionType.FREE : SubscriptionType.PREMIUM;
    }

    private String getQuarterType(QuarterGradeMarkType quarterGradeMarkType, Locale locale, Quarter quarter){
        String quarterMessage = "";
        if (quarterGradeMarkType == null)
            quarterGradeMarkType = QuarterGradeMarkType.QUARTER_GRADE;
        if (quarterGradeMarkType.equals(QuarterGradeMarkType.QUARTER_GRADE))
            quarterMessage = messageSource.getMessage("quarter", null, locale) + " : " + quarter.getQuarter()+"\n";
        String message = String.format("%s%s","quarter.mark.",quarterGradeMarkType.toString().toLowerCase());
        return messageSource.getMessage(message, null, locale)+"\n"+quarterMessage;
    }

    private void notifyUser(NotificationCreateRequest notificationCreateRequest, List<String> tokens, List<Long> userIds, FirebaseTokenDTO firebaseTokenDTO) {
        if (tokens.stream().noneMatch(s->!StringUtils.isEmpty(firebaseTokenDTO.getToken()) && s.equalsIgnoreCase(firebaseTokenDTO.getToken())))
            notifyToken(firebaseTokenDTO, notificationCreateRequest);
        if (userIds.stream().noneMatch(u->u.equals(firebaseTokenDTO.getUserId())))
            notificationService.create(notificationCreateRequest);

        if (!StringUtils.isEmpty(firebaseTokenDTO.getToken()))
            tokens.add(firebaseTokenDTO.getToken());
        userIds.add(firebaseTokenDTO.getUserId());
    }

    private void notifyToken(FirebaseTokenDTO firebaseTokenDTO, NotificationCreateRequest notificationCreateRequest){

        if (!StringUtils.isEmpty(firebaseTokenDTO.getToken())) {
            pushNotificationService.sendPushNotificationToToken(
                    getTokenPushNotificationRequest(notificationCreateRequest, firebaseTokenDTO.getToken()),
                    notificationCreateRequest
            );
        }

    }

    private PushNotificationRequest getTokenPushNotificationRequest(NotificationCreateRequest notificationCreateRequest, String token) {
        PushNotificationRequest pushNotificationRequest = new PushNotificationRequest(notificationCreateRequest.getTitle(), notificationCreateRequest.getContents(), "", token);
        BeanUtils.copyProperties(notificationCreateRequest, pushNotificationRequest);

        return pushNotificationRequest;
    }

    private String getLocalizedCourseTitle(Course course, String code) {
        String courseTitle;
        switch (code) {
            case "ru":
                courseTitle = !StringUtils.isEmpty(course.getTitleRu()) ? course.getTitleRu() : course.getTitle();
                break;
            case "kg":
                courseTitle = !StringUtils.isEmpty(course.getTitleKg()) ? course.getTitleKg() : course.getTitle();
                break;
            case "en":
                courseTitle = !StringUtils.isEmpty(course.getTitle()) ? course.getTitle() : course.getTitle();
                break;
            default:
                courseTitle = course.getTitle();
        }
        return courseTitle;
    }

    private Locale detectLanguageCode(String code) {
        Locale locale;
        if (StringUtils.isEmpty(code))
            return new Locale("ru");
        switch (code) {
            case "ru":
                locale = new Locale("ru");
                break;
            case "en":
                locale = new Locale("en");
                break;
            default:
                locale = new Locale("kg");
        }
        return locale;
    }
}
