package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.grade.*;
import kg.kundoluk.school.dto.notification.GradePushRequest;
import kg.kundoluk.school.dto.projection.GradeDTO;
import kg.kundoluk.school.endpoint.GradeEndpoint;
import kg.kundoluk.school.model.grade.Grade;
import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.enums.GradeMarkType;
import kg.kundoluk.school.model.instructor.CalendarTopic;
import kg.kundoluk.school.model.school.ShiftTime;
import kg.kundoluk.school.model.student.StudentCourse;
import kg.kundoluk.school.repository.CalendarTopicRepository;
import kg.kundoluk.school.repository.PersonRepository;
import kg.kundoluk.school.repository.ShiftTimeRepository;
import kg.kundoluk.school.repository.StudentCourseRepository;
import kg.kundoluk.school.security.jwt.JwtUser;
import kg.kundoluk.school.service.grade.GradeService;
import kg.kundoluk.school.service.rabbit.RabbitMessageProducer;
import kg.kundoluk.school.utils.TimeHelper;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Endpoint
public class GradeEndpointImpl implements GradeEndpoint {
    private final GradeService gradeService;
    private final PersonRepository personRepository;
    private final ShiftTimeRepository shiftTimeRepository;
    private final CalendarTopicRepository calendarTopicRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final RabbitMessageProducer rabbitMessageProducer;

    public GradeEndpointImpl(GradeService gradeService, PersonRepository personRepository, ShiftTimeRepository shiftTimeRepository, CalendarTopicRepository calendarTopicRepository, StudentCourseRepository studentCourseRepository, RabbitMessageProducer rabbitMessageProducer) {
        this.gradeService = gradeService;
        this.personRepository = personRepository;
        this.shiftTimeRepository = shiftTimeRepository;
        this.calendarTopicRepository = calendarTopicRepository;
        this.studentCourseRepository = studentCourseRepository;
        this.rabbitMessageProducer = rabbitMessageProducer;
    }

    @Override
    public void createBulkMobile(GradeMobileCreateDayRequest gradeMobileCreateDayRequest) {

        Person person = personRepository.getOne(gradeMobileCreateDayRequest.getPersonId());
        ShiftTime shiftTime = shiftTimeRepository.getOne(gradeMobileCreateDayRequest.getShiftTimeId());
        CalendarTopic calendarTopic = null;
        if (gradeMobileCreateDayRequest.getTopicId() != null)
            calendarTopic = calendarTopicRepository.getOne(gradeMobileCreateDayRequest.getTopicId());

        List<GradeRequest> gradeRequests = new ArrayList<>();
        for (GradeMobileStudentDto gradeMobileStudentDto: gradeMobileCreateDayRequest.getStudentList()){
            StudentCourse studentCourse = studentCourseRepository.getOne(gradeMobileStudentDto.getStudentCourseId());

            GradeRequest gradeRequest = GradeRequest.builder()
                    .calendarTopic(calendarTopic)
                    .gradeDate(LocalDate.parse(gradeMobileCreateDayRequest.getGradeDate(), TimeHelper.DATE_REVERSE_FORMATTER))
                    .gradeMarkType(gradeMobileStudentDto.getGradeMarkType())
                    .mark(gradeMobileStudentDto.getMark())
                    .person(person)
                    .shiftTime(shiftTime)
                    .studentCourse(studentCourse)
                    .build();

            gradeRequests.add(gradeRequest);
        }

        gradeService.createBulk(gradeRequests);

        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //TODO : send push notification parent
        for (GradeMobileStudentDto gradeMobileStudentDto: gradeMobileCreateDayRequest.getStudentList()){
            //PUSH NOTIFICATION
            rabbitMessageProducer.sendPushNotification(new GradePushRequest(gradeMobileStudentDto, jwtUser.getId()));
        }
    }

    @Override
    public void editBulkMobile(List<GradeMobileUpdateRequest> gradeMobileUpdateRequests) {

        List<Long> deleteIds = new ArrayList<>();
        for (GradeMobileUpdateRequest gradeMobileUpdateRequest: gradeMobileUpdateRequests){
            if (gradeMobileUpdateRequest.getGradeMarkType().equals(GradeMarkType.DELETE)){
                deleteIds.add(gradeMobileUpdateRequest.getGradeId());
            } else {
                GradeRequest gradeRequest = GradeRequest.builder()
                        .mark(gradeMobileUpdateRequest.getMark())
                        .gradeMarkType(gradeMobileUpdateRequest.getGradeMarkType())
                        .build();
                Grade grade = gradeService.findById(gradeMobileUpdateRequest.getGradeId());
                gradeService.edit(gradeRequest, grade);
            }
        }

        // DELETE
        if (deleteIds.size() > 0)
            gradeService.deleteInBatch(deleteIds);

        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (GradeMobileUpdateRequest gradeMobileUpdateRequest: gradeMobileUpdateRequests){
            if (gradeMobileUpdateRequest.getStudentCourseId() != null && gradeMobileUpdateRequest.getGradeMarkType().equals(GradeMarkType.GRADE)) {
                GradeMobileStudentDto gradeMobileStudentDto = new GradeMobileStudentDto();
                gradeMobileStudentDto.setGradeMarkType(gradeMobileUpdateRequest.getGradeMarkType());
                gradeMobileStudentDto.setMark(gradeMobileUpdateRequest.getMark());
                gradeMobileStudentDto.setStudentCourseId(gradeMobileUpdateRequest.getStudentCourseId());
                //PUSH NOTIFICATION
                rabbitMessageProducer.sendPushNotification(new GradePushRequest(gradeMobileStudentDto, jwtUser.getId()));
            }
        }

    }

    @Override
    public void deleteById(Long id) {
        gradeService.deleteById(id);
    }

    @Override
    public void deleteByTopic(Long topicId) {
        gradeService.deleteByTopic(topicId);
    }

    @Override
    public List<GradeMobileResponse> dayFetch(GradeMobileResponseRequest gradeMobileResponseRequest) {
        LocalDate start = LocalDate.parse(gradeMobileResponseRequest.getDate(), TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate end = LocalDate.parse(gradeMobileResponseRequest.getDate(), TimeHelper.DATE_REVERSE_FORMATTER);
        List<GradeDTO> grades = gradeService.findAllByPersonCourseClassDateRange(gradeMobileResponseRequest.getPersonId(), gradeMobileResponseRequest.getCourseId(), gradeMobileResponseRequest.getClassId(), start, end);
        return grades.stream().map(this::toGradeMobileResponse).collect(Collectors.toList());
    }

    @Override
    public List<GradeMobileResponse> monthFetch(GradeMobileResponseDateRequest gradeMobileResponseDateRequest) {
        LocalDate start = LocalDate.parse(gradeMobileResponseDateRequest.getStartDate(), TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate end = LocalDate.parse(gradeMobileResponseDateRequest.getEndDate(), TimeHelper.DATE_REVERSE_FORMATTER);
        Long personId = gradeMobileResponseDateRequest.getPersonId()!=null?gradeMobileResponseDateRequest.getPersonId():0L;
        Long courseId = gradeMobileResponseDateRequest.getCourseId()!=null?gradeMobileResponseDateRequest.getCourseId():0L;
        List<GradeDTO> grades = gradeService.findAllByPersonCourseClassDateRange(personId, courseId, gradeMobileResponseDateRequest.getClassId(), start, end);
        return grades.stream().map(this::toGradeMobileResponse).collect(Collectors.toList());
    }

    @Override
    public List<GradeMobileResponse> studentFetch(GradeMobileResponseStudentRequest gradeMobileResponseStudentRequest) {
        LocalDate start = LocalDate.parse(gradeMobileResponseStudentRequest.getStartDate(), TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate end = LocalDate.parse(gradeMobileResponseStudentRequest.getEndDate(), TimeHelper.DATE_REVERSE_FORMATTER);
        Long courseId = gradeMobileResponseStudentRequest.getCourseId()!=null?gradeMobileResponseStudentRequest.getCourseId():0L;
        List<GradeDTO> grades = gradeService.findAllByStudentCourseDateRange(gradeMobileResponseStudentRequest.getStudentId(), courseId, start, end);
        return grades.stream().map(this::toGradeMobileResponse).collect(Collectors.toList());
    }

    @Override
    public List<GradeMobileResponse> findAllByTopic(Long topicId) {
        return gradeService.findAllByTopic(topicId).stream().map(this::toGradeMobileResponse).collect(Collectors.toList());
    }

    @Override
    public List<GradeMobileResponse> findAllByShiftTime(Long shiftTimeId) {
        return gradeService.findAllByShiftTime(shiftTimeId).stream().map(this::toGradeMobileResponse).collect(Collectors.toList());
    }

    private GradeMobileResponse toGradeMobileResponse(GradeDTO grade){
        GradeMobileResponse gradeMobileResponse = new GradeMobileResponse();
        gradeMobileResponse.setGradeId(grade.getGradeId());
        gradeMobileResponse.setGradeMarkType(GradeMarkType.values()[grade.getGradeMarkType()]);
        gradeMobileResponse.setStudentCourseId(grade.getStudentCourseId());
        gradeMobileResponse.setGradeDate(grade.getGradeDate());
        gradeMobileResponse.setMark(grade.getMark());
        gradeMobileResponse.setShiftTimeId(grade.getShiftTimeId());
        gradeMobileResponse.setTopicId(grade.getTopicId());
        return gradeMobileResponse;
    }
}
