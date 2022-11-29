package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.assignment.*;
import kg.kundoluk.school.dto.attachment.AttachmentFileResponseDto;
import kg.kundoluk.school.dto.calendar.CalendarPlanCreateRequest;
import kg.kundoluk.school.dto.calendar.CalendarTopicCreateRequest;
import kg.kundoluk.school.endpoint.AssignmentEndpoint;
import kg.kundoluk.school.endpoint.CalendarPlanEndpoint;
import kg.kundoluk.school.endpoint.CalendarTopicEndpoint;
import kg.kundoluk.school.model.instructor.Assignment;
import kg.kundoluk.school.model.instructor.AssignmentAttachment;
import kg.kundoluk.school.model.instructor.CalendarPlan;
import kg.kundoluk.school.model.instructor.CalendarTopic;
import kg.kundoluk.school.repository.CalendarTopicRepository;
import kg.kundoluk.school.service.assignment.AssignmentAttachmentService;
import kg.kundoluk.school.service.assignment.AssignmentService;
import kg.kundoluk.school.service.async.AssignmentAttachmentAsyncUploadService;
import kg.kundoluk.school.service.rabbit.RabbitMessageProducer;
import kg.kundoluk.school.service.storage.AssignmentAttachFileService;
import kg.kundoluk.school.utils.TimeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Endpoint
public class AssignmentEndpointImpl implements AssignmentEndpoint {
    private final AssignmentService assignmentService;
    private final CalendarTopicRepository calendarTopicRepository;
    private final AssignmentAttachmentService assignmentAttachmentService;
    private final AssignmentAttachFileService assignmentAttachFileService;
    private final AssignmentAttachmentAsyncUploadService assignmentAttachmentAsyncUploadService;
    private final RabbitMessageProducer rabbitMessageProducer;
    private final CalendarPlanEndpoint calendarPlanEndpoint;
    private final CalendarTopicEndpoint calendarTopicEndpoint;

    private Logger logger = LoggerFactory.getLogger(AssignmentEndpointImpl.class);


    public AssignmentEndpointImpl(AssignmentService assignmentService, CalendarTopicRepository calendarTopicRepository, AssignmentAttachmentService assignmentAttachmentService, AssignmentAttachFileService assignmentAttachFileService, AssignmentAttachmentAsyncUploadService assignmentAttachmentAsyncUploadService, RabbitMessageProducer rabbitMessageProducer, CalendarPlanEndpoint calendarPlanEndpoint, CalendarTopicEndpoint calendarTopicEndpoint) {
        this.assignmentService = assignmentService;
        this.calendarTopicRepository = calendarTopicRepository;
        this.assignmentAttachmentService = assignmentAttachmentService;
        this.assignmentAttachFileService = assignmentAttachFileService;
        this.assignmentAttachmentAsyncUploadService = assignmentAttachmentAsyncUploadService;
        this.rabbitMessageProducer = rabbitMessageProducer;
        this.calendarPlanEndpoint = calendarPlanEndpoint;
        this.calendarTopicEndpoint = calendarTopicEndpoint;
    }

    @Override
    public Assignment create(AssignmentCreateRequest assignmentCreateRequest, MultipartFile[] multipartFiles) throws IOException {
        AssignmentRequest assignmentRequest = AssignmentRequest.builder()
                .calendarTopic(calendarTopicRepository.getOne(assignmentCreateRequest.getCalendarTopicId()))
                .content(assignmentCreateRequest.getContent())
                .title(assignmentCreateRequest.getTitle())
                .deadlineAt(LocalDate.parse(assignmentCreateRequest.getDeadlineAt(), TimeHelper.DATE_REVERSE_FORMATTER))
                .build();
        Assignment assignment = assignmentService.create(assignmentRequest);

        if (assignment != null) {
            if (multipartFiles != null ) {
                saveAttachments(multipartFiles, assignment);
                //assignmentAttachmentAsyncUploadService.uploadAttachments(multipartFiles, assignment);
            }

            // SEND PUSH NOTIFICATION PARENT
            rabbitMessageProducer.sendAssignmentNotification(assignmentCreateRequest);
            return assignment;
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        assignmentService.delete(id);
    }

    @Override
    public void deleteAttachment(Long id) {
        assignmentAttachmentService.delete(id);
    }

    @Override
    public void deleteAttachments(List<Long> ids) {
        assignmentAttachmentService.deleteList(ids);
    }

    @Override
    public void addAttachment(Assignment assignment, MultipartFile[] multipartFiles) throws IOException {
        if (multipartFiles != null)
            saveAttachments(multipartFiles, assignment);
    }

    @Override
    public Assignment createTopicLess(AssignmentCreateDefaultRequest assignmentCreateRequest, MultipartFile[] multipartFile) throws IOException {
        CalendarPlanCreateRequest calendarPlanCreateRequest = new CalendarPlanCreateRequest();
        calendarPlanCreateRequest.setTitle("автоматически созданная тема");
        calendarPlanCreateRequest.setChronicleId(assignmentCreateRequest.getChronicleId());
        calendarPlanCreateRequest.setClassId(assignmentCreateRequest.getClassId());
        calendarPlanCreateRequest.setCourseId(assignmentCreateRequest.getCourseId());
        calendarPlanCreateRequest.setPersonId(assignmentCreateRequest.getInstructorId());
        calendarPlanCreateRequest.setIsDefault(true);

        // CREATE CALENDAR PLAN
        CalendarPlan calendarPlan = calendarPlanEndpoint.create(calendarPlanCreateRequest);
        // CREATE CALENDAR TOPIC
        CalendarTopicCreateRequest calendarTopicCreateRequest = new CalendarTopicCreateRequest();
        calendarTopicCreateRequest.setCalendarPlanId(calendarPlan.getId());
        calendarTopicCreateRequest.setDatePlan(assignmentCreateRequest.getDeadlineAt());
        calendarTopicCreateRequest.setQuarterId(assignmentCreateRequest.getQuarterId());
        calendarTopicCreateRequest.setTitle(assignmentCreateRequest.getTitle());

        CalendarTopic calendarTopic = calendarTopicEndpoint.create(calendarTopicCreateRequest);
        // CREATE ASSIGNMENT
        AssignmentCreateRequest assignmentRequest = new AssignmentCreateRequest();
        assignmentRequest.setCalendarTopicId(calendarTopic.getId());
        assignmentRequest.setTitle(assignmentCreateRequest.getTitle());
        assignmentRequest.setDeadlineAt(assignmentCreateRequest.getDeadlineAt());
        assignmentRequest.setContent(assignmentCreateRequest.getContent());

        return this.create(assignmentRequest, multipartFile);
    }

    @Override
    public AssignmentResponse findById(Long id) {
        return assignmentResponse(assignmentService.findById(id));
    }

    @Override
    public Assignment edit(AssignmentCreateRequest assignmentCreateRequest, MultipartFile[] multipartFiles, Assignment assignment) throws IOException {
        AssignmentRequest assignmentRequest = AssignmentRequest.builder()
                .calendarTopic(calendarTopicRepository.getOne(assignmentCreateRequest.getCalendarTopicId()))
                .content(assignmentCreateRequest.getContent())
                .title(assignmentCreateRequest.getTitle())
                .deadlineAt(LocalDate.parse(assignmentCreateRequest.getDeadlineAt(), TimeHelper.DATE_REVERSE_FORMATTER))
                .build();
        addAttachment(assignment, multipartFiles);
        return assignmentService.edit(assignmentRequest, assignment);
    }

    @Override
    public Page<Assignment> findAllByInstructorClassCourse(Long instructorId, Long classId, Long courseId, Long chronicleId, Pageable pageable) {
        return assignmentService.findAllByInstructorClassCourse(instructorId, classId, courseId, chronicleId, pageable);
    }

    @Override
    public Page<Assignment> findAllByClass(Long classId, Long chronicleId, Pageable pageable) {
        return assignmentService.findAllByClass(classId, chronicleId, pageable);
    }

    private void saveAttachments(MultipartFile[] multipartFiles, Assignment assignment) throws IOException {
        for (MultipartFile file: multipartFiles) {

            AttachmentFileResponseDto attachmentFileResponseDto = assignmentAttachFileService.uploadMultipartFile(file);
            AssignmentAttachmentRequest assignmentAttachmentRequest = AssignmentAttachmentRequest.builder()
                    .assignment(assignment)
                    .fileUrl(attachmentFileResponseDto.getUrl())
                    .originalTitle(attachmentFileResponseDto.getFilename())
                    .build();
            assignmentAttachmentService.create(assignmentAttachmentRequest);
        }
    }

    private AssignmentResponse assignmentResponse(Assignment assignment){

        AssignmentResponse assignmentResponse = new AssignmentResponse();
        assignmentResponse.setId(assignment.getId());
        assignmentResponse.setCalendarTopicId(assignment.getCalendarTopic().getId());
        assignmentResponse.setCalendarTopicTitle(assignment.getCalendarTopic().getTitle());
        assignmentResponse.setContent(assignment.getContent());
        assignmentResponse.setClassTitle(assignment.getCalendarTopic().getCalendarPlan().getSchoolClass().getSelectorTitle());
        assignmentResponse.setCourseTitle(assignment.getCalendarTopic().getCalendarPlan().getSchoolCourse().getCourse().getTitle());
        assignmentResponse.setDeadlineAt(TimeHelper.DATE_REVERSE_FORMATTER.format(assignment.getDeadlineAt()));

        if (assignment.getAttachments()!=null){
            List<AssignmentAttachmentResponse> assignmentAttachmentResponses = new ArrayList<>();
            for (AssignmentAttachment assignmentAttachment: assignment.getAttachments()) {
                AssignmentAttachmentResponse assignmentAttachmentResponse = new AssignmentAttachmentResponse();
                assignmentAttachmentResponse.setId(assignmentAttachment.getId());
                assignmentAttachmentResponse.setOriginalTitle(assignmentAttachment.getOriginalTitle());
                assignmentAttachmentResponse.setFileUrl(assignmentAttachment.getFileUrl());
                assignmentAttachmentResponses.add(assignmentAttachmentResponse);
            }
            assignmentResponse.setAttachments(assignmentAttachmentResponses);
        }

        return assignmentResponse;
    }

    /*@Override
    public List<AssignmentResponse> findAllByInstructor(AssignmentFilterRequestDto assignmentFilterRequestDto) {

        *//*AssignmentSearchRequest searchRequest = assignmentFilterRequestDto.getSearchRequest();
        Pageable pageable = pageBaleMapper.toPageable(assignmentFilterRequestDto.getPageRequest());
        Page<Assignment> assignments = assignmentService.findAllByInstructor(searchRequest.getInstructorId(), searchRequest.getChronicleId(), pageable);

        Page<AssignmentResponse> responses = assignments.map(this::assignmentResponse);

        return pageBaleMapper.toPageResponse(responses);*//*

        AssignmentSearchRequest searchRequest = assignmentFilterRequestDto.getSearchRequest();
        List<Assignment> assignments = assignmentService.findAllByInstructor(searchRequest.getInstructorId(), searchRequest.getChronicleId());

        return assignments.stream().map(this::assignmentResponse).collect(Collectors.toList());
    }*/

}
