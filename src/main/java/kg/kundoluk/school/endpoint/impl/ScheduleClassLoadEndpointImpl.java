package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.schedule.ScheduleClassLoadCreateRequest;
import kg.kundoluk.school.dto.schedule.ScheduleClassLoadRequest;
import kg.kundoluk.school.dto.schedule.ScheduleClassLoadResponse;
import kg.kundoluk.school.dto.schedule.ScheduleClassLoadUpdateRequest;
import kg.kundoluk.school.endpoint.ScheduleClassLoadEndpoint;
import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.schedule.ScheduleClassLoad;
import kg.kundoluk.school.model.school.SchoolClass;
import kg.kundoluk.school.model.school.SchoolCourse;
import kg.kundoluk.school.repository.PersonRepository;
import kg.kundoluk.school.repository.SchoolClassRepository;
import kg.kundoluk.school.repository.SchoolCourseRepository;
import kg.kundoluk.school.service.schedule.ScheduleClassLoadService;

import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class ScheduleClassLoadEndpointImpl implements ScheduleClassLoadEndpoint {
    private final ScheduleClassLoadService scheduleClassLoadService;
    private final SchoolClassRepository schoolClassRepository;
    private final PersonRepository personRepository;
    private final SchoolCourseRepository schoolCourseRepository;

    public ScheduleClassLoadEndpointImpl(ScheduleClassLoadService scheduleClassLoadService, SchoolClassRepository schoolClassRepository, PersonRepository personRepository, SchoolCourseRepository schoolCourseRepository) {
        this.scheduleClassLoadService = scheduleClassLoadService;
        this.schoolClassRepository = schoolClassRepository;
        this.personRepository = personRepository;
        this.schoolCourseRepository = schoolCourseRepository;
    }

    @Override
    public void create(ScheduleClassLoadCreateRequest scheduleClassLoadCreateRequest) {
        ScheduleClassLoadRequest scheduleClassLoadRequest = getScheduleClassLoadRequest(scheduleClassLoadCreateRequest);

        scheduleClassLoadService.create(scheduleClassLoadRequest);
    }

    private ScheduleClassLoadRequest getScheduleClassLoadRequest(ScheduleClassLoadCreateRequest scheduleClassLoadCreateRequest) {
        SchoolClass schoolClass = schoolClassRepository.getOne(scheduleClassLoadCreateRequest.getClassId());
        Person person = personRepository.getOne(scheduleClassLoadCreateRequest.getPersonId());
        SchoolCourse schoolCourse = schoolCourseRepository.getOne(scheduleClassLoadCreateRequest.getCourseId());

        return ScheduleClassLoadRequest.builder()
                .person(person)
                .schoolClass(schoolClass)
                .schoolCourse(schoolCourse)
                .hourCount(scheduleClassLoadCreateRequest.getHourLoad())
                .build();
    }


    @Override
    public void createBulk(List<ScheduleClassLoadCreateRequest> scheduleClassLoadCreateRequests) {

        scheduleClassLoadService.createBulk(
                scheduleClassLoadCreateRequests
                .stream()
                .map(this::getScheduleClassLoadRequest)
                .collect(Collectors.toList())
        );

    }

    @Override
    public void editBulk(List<ScheduleClassLoadUpdateRequest> scheduleClassLoadUpdateRequests) {
        for (ScheduleClassLoadUpdateRequest scheduleClassLoadUpdateRequest: scheduleClassLoadUpdateRequests){
            scheduleClassLoadService.editHour(scheduleClassLoadUpdateRequest.getId(), scheduleClassLoadUpdateRequest.getHourLoad());
        }
    }

    @Override
    public void delete(Long id) {
        scheduleClassLoadService.delete(id);
    }

    @Override
    public List<ScheduleClassLoadResponse> findAllBySchool(Long schoolId) {
        return scheduleClassLoadService.findAllBySchool(schoolId).stream().map(this::toScheduleClassLoadResponse).collect(Collectors.toList());
    }

    private ScheduleClassLoadResponse toScheduleClassLoadResponse(ScheduleClassLoad scheduleClassLoad){
        ScheduleClassLoadResponse scheduleClassLoadResponse = new ScheduleClassLoadResponse();
        scheduleClassLoadResponse.setId(scheduleClassLoad.getId());
        scheduleClassLoadResponse.setClassId(scheduleClassLoad.getSchoolClass().getId());
        scheduleClassLoadResponse.setClassTitle(scheduleClassLoad.getSchoolClass().getSelectorTitle());
        scheduleClassLoadResponse.setCourseTitle(scheduleClassLoad.getSchoolCourse().getCourse().getTitle());
        scheduleClassLoadResponse.setCourseTitleKG(scheduleClassLoad.getSchoolCourse().getCourse().getTitleKg());
        scheduleClassLoadResponse.setCourseTitleRU(scheduleClassLoad.getSchoolCourse().getCourse().getTitleRu());
        scheduleClassLoadResponse.setPersonTitle(scheduleClassLoad.getPerson().getSelectorTitle());
        scheduleClassLoadResponse.setHourLoad(scheduleClassLoad.getHourLoad());

        return scheduleClassLoadResponse;
    }
}
