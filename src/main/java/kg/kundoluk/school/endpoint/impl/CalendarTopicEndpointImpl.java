package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.calendar.CalendarTopicCreateRequest;
import kg.kundoluk.school.dto.calendar.CalendarTopicRequest;
import kg.kundoluk.school.dto.calendar.CalendarTopicResponse;
import kg.kundoluk.school.dto.calendar.CalendarTopicUpdateRequest;
import kg.kundoluk.school.dto.projection.CalendarTopicDTO;
import kg.kundoluk.school.endpoint.CalendarTopicEndpoint;
import kg.kundoluk.school.exception.ConstraintMappingException;
import kg.kundoluk.school.model.instructor.CalendarTopic;
import kg.kundoluk.school.repository.CalendarPlanRepository;
import kg.kundoluk.school.repository.QuarterRepository;
import kg.kundoluk.school.service.calendar.CalendarTopicService;
import kg.kundoluk.school.utils.TimeHelper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class CalendarTopicEndpointImpl implements CalendarTopicEndpoint {
    private final CalendarTopicService calendarTopicService;
    private final CalendarPlanRepository calendarPlanRepository;
    private final QuarterRepository quarterRepository;

    public CalendarTopicEndpointImpl(CalendarTopicService calendarTopicService, CalendarPlanRepository calendarPlanRepository, QuarterRepository quarterRepository) {
        this.calendarTopicService = calendarTopicService;
        this.calendarPlanRepository = calendarPlanRepository;
        this.quarterRepository = quarterRepository;
    }

    @Override
    public CalendarTopic create(CalendarTopicCreateRequest calendarTopicCreateRequest) {
        CalendarTopicRequest calendarTopicRequest = CalendarTopicRequest.builder()
                .calendarPlan(calendarPlanRepository.getOne(calendarTopicCreateRequest.getCalendarPlanId()))
                .quarter(quarterRepository.getOne(calendarTopicCreateRequest.getQuarterId()))
                .datePlan(LocalDate.parse(calendarTopicCreateRequest.getDatePlan(), TimeHelper.DATE_REVERSE_FORMATTER))
                .hours(calendarTopicCreateRequest.getHours())
                .title(calendarTopicCreateRequest.getTitle())
                .isPassed(false)
                .topicResult(calendarTopicCreateRequest.getTopicResult())
                .topicVisibility(calendarTopicCreateRequest.getTopicVisibility())
                .build();
        return calendarTopicService.create(calendarTopicRequest);
    }

    @Override
    public CalendarTopic edit(CalendarTopicUpdateRequest calendarTopicUpdateRequest, CalendarTopic calendarTopic) {
        CalendarTopicRequest calendarTopicRequest = CalendarTopicRequest.builder()
                .quarter(quarterRepository.getOne(calendarTopicUpdateRequest.getQuarterId()))
                .isPassed(calendarTopicUpdateRequest.getIsPassed())
                .datePlan(LocalDate.parse(calendarTopicUpdateRequest.getDatePlan(), TimeHelper.DATE_REVERSE_FORMATTER))
                .hours(calendarTopicUpdateRequest.getHours())
                .title(calendarTopicUpdateRequest.getTitle())
                .topicResult(calendarTopicUpdateRequest.getTopicResult())
                .topicVisibility(calendarTopicUpdateRequest.getTopicVisibility())
                .build();
        return calendarTopicService.edit(calendarTopicRequest, calendarTopic);
    }

    @Override
    public List<CalendarTopicResponse> findAllByCalendarPlan(Long calendarPlanId) {
        return calendarTopicService.findAllByCalendarPlan(calendarPlanId).stream().map(this::toCalendarTopicResponse).collect(Collectors.toList());
    }

    @Override
    public List<CalendarTopicResponse> findAllByInstructorClassCourse(Long instructorId, Long classId, Long courseId, Long chronicleId) {
        return calendarTopicService.findAllByInstructorClassCourse(instructorId, classId, courseId, chronicleId).stream().map(this::toCalendarTopicResponse).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) throws ConstraintMappingException {
        calendarTopicService.delete(id);
    }

    @Override
    public CalendarTopic findById(Long id) {
        return calendarTopicService.findById(id);
    }

    private CalendarTopicResponse toCalendarTopicResponse(CalendarTopic calendarTopic){
        CalendarTopicResponse calendarTopicResponse = new CalendarTopicResponse();
        calendarTopicResponse.setId(calendarTopic.getId());
        calendarTopicResponse.setTitle(calendarTopic.getTitle());
        calendarTopicResponse.setQuarterId(calendarTopic.getQuarter().getId());
        calendarTopicResponse.setQuarterSemester(calendarTopic.getQuarter().getQuarter());
        calendarTopicResponse.setTopicHour(calendarTopic.getHours());
        calendarTopicResponse.setTopicVisibility(calendarTopic.getTopicVisibility());
        calendarTopicResponse.setTopicResult(calendarTopic.getTopicResult());
        calendarTopicResponse.setIsPassed(calendarTopic.getIsPassed());
        if (calendarTopic.getDatePlan()!=null)
            calendarTopicResponse.setTopicDate(TimeHelper.DATE_REVERSE_FORMATTER.format(calendarTopic.getDatePlan()));
        return calendarTopicResponse;
    }

    private CalendarTopicResponse toCalendarTopicResponse(CalendarTopicDTO calendarTopic){
        CalendarTopicResponse calendarTopicResponse = new CalendarTopicResponse();
        calendarTopicResponse.setId(calendarTopic.getId());
        calendarTopicResponse.setTitle(calendarTopic.getTitle());
        calendarTopicResponse.setQuarterId(calendarTopic.getQuarterId());
        calendarTopicResponse.setTopicResult(calendarTopic.getTopicResult());
        calendarTopicResponse.setTopicVisibility(calendarTopic.getTopicVisibility());
        calendarTopicResponse.setTopicDate(calendarTopic.getTopicDate());
        calendarTopicResponse.setTopicHour(calendarTopic.getTopicHour());
        calendarTopicResponse.setIsPassed(calendarTopic.getIsPassed());
        return calendarTopicResponse;
    }
}
