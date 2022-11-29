package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.calendar.*;
import kg.kundoluk.school.endpoint.CalendarPlanEndpoint;
import kg.kundoluk.school.exception.ConstraintMappingException;
import kg.kundoluk.school.model.instructor.CalendarPlan;
import kg.kundoluk.school.model.instructor.CalendarTopic;
import kg.kundoluk.school.repository.ChronicleRepository;
import kg.kundoluk.school.repository.PersonRepository;
import kg.kundoluk.school.repository.SchoolClassRepository;
import kg.kundoluk.school.repository.SchoolCourseRepository;
import kg.kundoluk.school.service.calendar.CalendarPlanService;
import kg.kundoluk.school.service.calendar.CalendarTopicService;

import java.util.ArrayList;
import java.util.List;

@Endpoint
public class CalendarPlanEndpointImpl implements CalendarPlanEndpoint {
    private final CalendarPlanService calendarPlanService;
    private final PersonRepository personRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SchoolCourseRepository schoolCourseRepository;
    private final ChronicleRepository chronicleRepository;
    private final CalendarTopicService calendarTopicService;

    public CalendarPlanEndpointImpl(CalendarPlanService calendarPlanService, PersonRepository personRepository, SchoolClassRepository schoolClassRepository, SchoolCourseRepository schoolCourseRepository, ChronicleRepository chronicleRepository, CalendarTopicService calendarTopicService) {
        this.calendarPlanService = calendarPlanService;
        this.personRepository = personRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.schoolCourseRepository = schoolCourseRepository;
        this.chronicleRepository = chronicleRepository;
        this.calendarTopicService = calendarTopicService;
    }

    //@CacheEvict(value = CacheService.CALENDAR_PLAN, key = "#calendarPlanCreateRequest.personId+'-'+#calendarPlanCreateRequest.chronicleId")
    @Override
    public CalendarPlan create(CalendarPlanCreateRequest calendarPlanCreateRequest) {
        CalendarPlan calendarPlan = calendarPlanService.findByInstructorClassCourseChronicle(calendarPlanCreateRequest.getPersonId(), calendarPlanCreateRequest.getClassId(), calendarPlanCreateRequest.getCourseId(), calendarPlanCreateRequest.getChronicleId());
        if (calendarPlan != null){
            return calendarPlan;
        }

        CalendarPlanRequest calendarPlanRequest = CalendarPlanRequest.builder()
                .chronicleYear(chronicleRepository.getOne(calendarPlanCreateRequest.getChronicleId()))
                .person(personRepository.getOne(calendarPlanCreateRequest.getPersonId()))
                .schoolClass(schoolClassRepository.getOne(calendarPlanCreateRequest.getClassId()))
                .schoolCourse(schoolCourseRepository.getOne(calendarPlanCreateRequest.getCourseId()))
                .title(calendarPlanCreateRequest.getTitle())
                .isDefault(calendarPlanCreateRequest.getIsDefault())
                .build();
        return calendarPlanService.create(calendarPlanRequest);
    }

    @Override
    public CalendarPlan edit(String title, CalendarPlan calendarPlan) {
        CalendarPlanRequest calendarPlanRequest = CalendarPlanRequest.builder()
                .title(title)
                .build();
        return calendarPlanService.edit(calendarPlanRequest, calendarPlan);
    }

    @Override
    public CalendarPlan edit(CalendarPlanUpdateRequest calendarPlanUpdateRequest, CalendarPlan calendarPlan) {
        CalendarPlanRequest calendarPlanRequest = CalendarPlanRequest.builder()
                .title(calendarPlanUpdateRequest.getTitle())
                .schoolCourse(schoolCourseRepository.getOne(calendarPlanUpdateRequest.getCourseId()))
                .schoolClass(schoolClassRepository.getOne(calendarPlanUpdateRequest.getClassId()))
                .build();
        return calendarPlanService.edit(calendarPlanRequest, calendarPlan);
    }

    @Override
    public CalendarPlanResponse getById(Long id) {
        return toCalendarPlanResponse(calendarPlanService.findById(id));
    }

    //@Cacheable(value = CacheService.CALENDAR_PLAN, key = "#instructorId+'-'+#chronicleId")
    @Override
    public List<CalendarPlanResponse> findAllByInstructorChronicle(Long instructorId, Long chronicleId) {
        List<CalendarPlan> calendarPlans = calendarPlanService.findAllByInstructorChronicle(instructorId, chronicleId);

        List<CalendarPlanResponse> responses = new ArrayList<>();
        for (CalendarPlan calendarPlan: calendarPlans){
            responses.add(toCalendarPlanResponse(calendarPlan));
        }
        return responses;
    }

    @Override
    public Integer clone(CalendarPlanCloneCreateRequest calendarPlanCloneCreateRequest) {
        CalendarPlan calendarPlan = calendarPlanService.findById(calendarPlanCloneCreateRequest.getCalendarPlanId());

        CalendarPlan newPlan = createPlan(calendarPlanCloneCreateRequest, calendarPlan);

        int count = 0;
        for (CalendarTopic calendarTopic: calendarPlan.getTopicSet()){
            CalendarTopicRequest calendarTopicRequest = CalendarTopicRequest.builder()
                    .topicVisibility(calendarTopic.getTopicVisibility())
                    .topicResult(calendarTopic.getTopicResult())
                    .title(calendarTopic.getTitle())
                    .hours(calendarTopic.getHours())
                    .datePlan(calendarTopic.getDatePlan())
                    .quarter(calendarTopic.getQuarter())
                    .calendarPlan(newPlan)
                    .build();
            calendarTopicService.create(calendarTopicRequest);
            count++;
        }

        return count;
    }

    private CalendarPlan createPlan(CalendarPlanCloneCreateRequest calendarPlanCloneCreateRequest, CalendarPlan calendarPlan) {
        CalendarPlanRequest calendarPlanRequest = CalendarPlanRequest.builder()
                .chronicleYear(chronicleRepository.getOne(calendarPlanCloneCreateRequest.getChronicleId()))
                .person(calendarPlan.getPerson())
                .schoolClass(schoolClassRepository.getOne(calendarPlanCloneCreateRequest.getClassId()))
                .schoolCourse(calendarPlan.getSchoolCourse())
                .isDefault(false)
                .title(calendarPlanCloneCreateRequest.getTitle())
                .build();
        return calendarPlanService.create(calendarPlanRequest);
    }

    @Override
    public void delete(Long id) throws ConstraintMappingException {
        calendarPlanService.delete(id);
    }

    private CalendarPlanResponse toCalendarPlanResponse(CalendarPlan calendarPlan){
        CalendarPlanResponse calendarPlanResponse = new CalendarPlanResponse();

        calendarPlanResponse.setId(calendarPlan.getId());
        calendarPlanResponse.setTitle(calendarPlan.getTitle());
        calendarPlanResponse.setClassTitle(calendarPlan.getSchoolClass().getSelectorTitle());
        calendarPlanResponse.setClassId(calendarPlan.getSchoolClass().getId());
        calendarPlanResponse.setSchoolId(calendarPlan.getSchoolClass().getSchool().getId());
        calendarPlanResponse.setCourseId(calendarPlan.getSchoolCourse().getId());
        calendarPlanResponse.setCourseTitle(calendarPlan.getSchoolCourse().getCourse().getTitle());
        calendarPlanResponse.setCourseTitleKG(calendarPlan.getSchoolCourse().getCourse().getTitleKg());
        calendarPlanResponse.setCourseTitleRU(calendarPlan.getSchoolCourse().getCourse().getTitleRu());
        calendarPlanResponse.setTopicTotalCount(calendarPlan.getTopicSet().size());
        calendarPlanResponse.setTopicTotalHour(calendarPlan.getTopicSet().stream().mapToInt(val -> val.getHours() == null ? 1 : val.getHours()).sum());

        return calendarPlanResponse;
    }
}
