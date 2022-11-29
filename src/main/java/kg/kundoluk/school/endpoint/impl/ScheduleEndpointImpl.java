package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.projection.GradeDTO;
import kg.kundoluk.school.dto.projection.ScheduleInstructorDTO;
import kg.kundoluk.school.dto.schedule.*;
import kg.kundoluk.school.endpoint.ScheduleEndpoint;
import kg.kundoluk.school.exception.AlreadyExistException;
import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.enums.WeekDay;
import kg.kundoluk.school.model.schedule.Schedule;
import kg.kundoluk.school.model.school.*;
import kg.kundoluk.school.repository.*;
import kg.kundoluk.school.service.async.StudentCourseGenerateService;
import kg.kundoluk.school.service.grade.GradeService;
import kg.kundoluk.school.service.schedule.ScheduleService;
import kg.kundoluk.school.utils.TimeHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class ScheduleEndpointImpl implements ScheduleEndpoint {
    private final ScheduleService scheduleService;
    private final SchoolClassRepository schoolClassRepository;
    private final ShiftTimeRepository shiftTimeRepository;
    private final PersonRepository personRepository;
    private final SchoolCourseRepository schoolCourseRepository;
    private final ScheduleGroupRepository scheduleGroupRepository;
    private final StudentCourseGenerateService studentCourseGenerateService;
    private final GradeService gradeService;

    public ScheduleEndpointImpl(ScheduleService scheduleService, SchoolClassRepository schoolClassRepository, ShiftTimeRepository shiftTimeRepository, PersonRepository personRepository, SchoolCourseRepository schoolCourseRepository, ScheduleGroupRepository scheduleGroupRepository, StudentCourseGenerateService studentCourseGenerateService, GradeService gradeService) {
        this.scheduleService = scheduleService;
        this.schoolClassRepository = schoolClassRepository;
        this.shiftTimeRepository = shiftTimeRepository;
        this.personRepository = personRepository;
        this.schoolCourseRepository = schoolCourseRepository;
        this.scheduleGroupRepository = scheduleGroupRepository;
        this.studentCourseGenerateService = studentCourseGenerateService;
        this.gradeService = gradeService;
    }

    @Override
    public void create(ScheduleCreateRequest scheduleCreateRequest) throws AlreadyExistException {
        Person person = personRepository.getOne(scheduleCreateRequest.getInstructorId());
        ShiftTime shiftTime = shiftTimeRepository.getOne(scheduleCreateRequest.getShiftTimeId());
        SchoolCourse schoolCourse = schoolCourseRepository.getOne(scheduleCreateRequest.getCourseId());
        SchoolClass schoolClass = schoolClassRepository.getOne(scheduleCreateRequest.getClassId());

        ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                .person(person)
                .schoolClass(schoolClass)
                .schoolCourse(schoolCourse)
                .shiftTime(shiftTime)
                .weekDay(scheduleCreateRequest.getWeekDay())
                .groupTitle(scheduleCreateRequest.getGroupTitle())
                .isGroup(scheduleCreateRequest.getIsGroup())
                .scheduleGroup(scheduleCreateRequest.getIsGroup()?scheduleGroupRepository.getOne(scheduleCreateRequest.getScheduleGroupId()):null)
                .build();
        scheduleService.create(scheduleRequest);

        // CREATE STUDENT COURSES
        studentCourseGenerateService.generateCourse(scheduleCreateRequest, person, schoolCourse, schoolClass);
    }

    @Override
    public void createMassive(List<ScheduleCreateRequest> scheduleCreateRequestList) {
        List<ScheduleRequest> scheduleRequests = new ArrayList<>();
        for (ScheduleCreateRequest scheduleCreateRequest: scheduleCreateRequestList){
            Person person = personRepository.getOne(scheduleCreateRequest.getInstructorId());
            ShiftTime shiftTime = shiftTimeRepository.getOne(scheduleCreateRequest.getShiftTimeId());
            SchoolCourse schoolCourse = schoolCourseRepository.getOne(scheduleCreateRequest.getCourseId());
            SchoolClass schoolClass = schoolClassRepository.getOne(scheduleCreateRequest.getClassId());

            ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                    .person(person)
                    .schoolClass(schoolClass)
                    .schoolCourse(schoolCourse)
                    .shiftTime(shiftTime)
                    .weekDay(scheduleCreateRequest.getWeekDay())
                    .isGroup(false)
                    .build();
            scheduleRequests.add(scheduleRequest);
        }
        scheduleService.createMassive(scheduleRequests);
    }

    @Override
    public void edit(ScheduleUpdateRequest scheduleUpdateRequest, Schedule schedule) {
        Person person = personRepository.getOne(scheduleUpdateRequest.getInstructorId());
        SchoolCourse schoolCourse = schoolCourseRepository.getOne(scheduleUpdateRequest.getCourseId());
        ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                .person(person)
                .schoolCourse(schoolCourse)
                .groupTitle(scheduleUpdateRequest.getGroupTitle())
                .isGroup(scheduleUpdateRequest.getIsGroup())
                .scheduleGroup(scheduleUpdateRequest.getScheduleGroupId()!=null?scheduleGroupRepository.getOne(scheduleUpdateRequest.getScheduleGroupId()):null)
                .build();
        scheduleService.edit(scheduleRequest, schedule);
    }

    @Override
    public void delete(Long id) {
        scheduleService.delete(id);
    }

    @Override
    public void deleteBySchool(Long schoolId, Long shiftId) {
        scheduleService.deleteBySchool(schoolId, shiftId);
    }

    @Override
    public ScheduleResponse findById(Long id) {
        return toScheduleResponse(scheduleService.findById(id));
    }

    @Override
    public List<ScheduleResponse> findAllBySchool(Long schoolId) {
        return scheduleService.listAllBySchool(schoolId).stream().map(this::toScheduleResponse).collect(Collectors.toList());
    }

    @Override
    public List<ScheduleResponse> findAllByClass(Long classId) {
        return scheduleService.listAllByClass(classId).stream().map(this::toScheduleResponse).collect(Collectors.toList());
    }

    @Override
    public List<ScheduleResponse> findAllByInstructorDate(Long instructorId, String date) {
        int dayOfWeek = getDayOfWeek(date);
        return scheduleService.listAllByInstructorAndDay(instructorId, WeekDay.values()[dayOfWeek]).stream().map(this::toScheduleResponse).collect(Collectors.toList());
    }

    private int getDayOfWeek(String date) {
        LocalDate localDate = LocalDate.parse(date, TimeHelper.DATE_REVERSE_FORMATTER);
        int dayOfWeek = localDate.getDayOfWeek().getValue();
        if (dayOfWeek == 7) dayOfWeek = 0;
        return dayOfWeek;
    }

    @Override
    public List<ScheduleResponse> findAllByClassDate(Long classId, String date) {
        int dayOfWeek = getDayOfWeek(date);
        return scheduleService.listAllByClassAndDay(classId, WeekDay.values()[dayOfWeek]).stream().map(this::toScheduleResponse).collect(Collectors.toList());
    }

    @Override
    public List<ScheduleResponse> findAllByInstructor(Long instructorId) {
        return scheduleService.listAllByInstructor(instructorId).stream().map(this::toScheduleResponse).collect(Collectors.toList());
    }

    @Override
    public List<ScheduleMonthResponse> findAllInstructorScheduleMonth(Long instructorId, Long classId, Long courseId, String startDate, String endDate) {
        List<ScheduleInstructorDTO> schedules = scheduleService.listAllByInstructorClassCourse(instructorId, classId, courseId);

        List<ScheduleMonthResponse> result = new ArrayList<>();
        LocalDate start = LocalDate.parse(startDate, TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate end = LocalDate.parse(endDate, TimeHelper.DATE_REVERSE_FORMATTER);

        for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
            int wDay = d.getDayOfWeek().getValue();
            List<ScheduleInstructorDTO> filteredDays = schedules.stream().filter(w->w.getWeekDay().equals(wDay)).collect(Collectors.toList());
            for (ScheduleInstructorDTO scheduleInstructorDTO: filteredDays){
                ScheduleMonthResponse scheduleMonthResponse = new ScheduleMonthResponse();
                scheduleMonthResponse.setDay(d.toString());
                scheduleMonthResponse.setShiftTimeId(scheduleInstructorDTO.getShiftTimeId());
                scheduleMonthResponse.setShiftTimeTitle(scheduleInstructorDTO.getShiftTimeTitle());
                result.add(scheduleMonthResponse);
            }


        }
        return result;
    }

    @Override
    public List<ScheduleMonthResponse> findAllClassScheduleMonth(Long classId, Long courseId, String startDate, String endDate) {
        Long instructorId = 0L;
        if (courseId == null)
            courseId = 0L;

        return findAllInstructorScheduleMonth(instructorId, classId, courseId, startDate, endDate);
    }

    private ScheduleResponse toScheduleResponse(Schedule schedule){
        ScheduleResponse scheduleResponse = new ScheduleResponse();
        scheduleResponse.setId(schedule.getId());
        scheduleResponse.setClassId(schedule.getSchoolClass().getId());
        scheduleResponse.setClassTitle(schedule.getSchoolClass().getSelectorTitle());
        scheduleResponse.setCourseId(schedule.getSchoolCourse().getId());
        scheduleResponse.setCourseTitle(schedule.getSchoolCourse().getCourse().getTitle());
        scheduleResponse.setCourseTitleKG(schedule.getSchoolCourse().getCourse().getTitleKg());
        scheduleResponse.setCourseTitleRU(schedule.getSchoolCourse().getCourse().getTitleRu());
        scheduleResponse.setInstructorId(schedule.getPerson().getId());
        scheduleResponse.setInstructorTitle(schedule.getPerson().getSelectorTitle());
        scheduleResponse.setGroupTitle(schedule.getGroupTitle());
        scheduleResponse.setIsGroup(schedule.getIsGroup());
        scheduleResponse.setShiftTimeId(schedule.getShiftTime().getId());
        scheduleResponse.setWeekDay(schedule.getWeekDay());
        scheduleResponse.setSchoolId(schedule.getSchoolClass().getSchool().getId());
        if (schedule.getScheduleGroup()!=null)
            scheduleResponse.setScheduleGroupId(schedule.getScheduleGroup().getId());

        return scheduleResponse;
    }
}
