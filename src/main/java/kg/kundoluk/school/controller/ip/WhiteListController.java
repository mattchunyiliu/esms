package kg.kundoluk.school.controller.ip;

import kg.kundoluk.school.dto.person.PersonUserDto;
import kg.kundoluk.school.dto.projection.*;
import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.school.School;
import kg.kundoluk.school.service.person.PersonService;
import kg.kundoluk.school.service.schedule.ScheduleClassLoadService;
import kg.kundoluk.school.service.schedule.ScheduleCourseConstraintService;
import kg.kundoluk.school.service.schedule.SchedulePersonConstraintService;
import kg.kundoluk.school.service.school.SchoolClassService;
import kg.kundoluk.school.service.school.SchoolCourseService;
import kg.kundoluk.school.service.school.SchoolService;
import kg.kundoluk.school.service.school.ShiftTimeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/white-list")
public class WhiteListController {
    private final SchoolService schoolService;
    private final PersonService personService;
    private final SchoolClassService schoolClassService;
    private final ShiftTimeService shiftTimeService;
    private final SchoolCourseService schoolCourseService;
    private final SchedulePersonConstraintService schedulePersonConstraintService;
    private final ScheduleCourseConstraintService scheduleCourseConstraintService;
    private final ScheduleClassLoadService scheduleClassLoadService;

    public WhiteListController(SchoolService schoolService, PersonService personService, SchoolClassService schoolClassService, ShiftTimeService shiftTimeService, SchoolCourseService schoolCourseService, SchedulePersonConstraintService schedulePersonConstraintService, ScheduleCourseConstraintService scheduleCourseConstraintService, ScheduleClassLoadService scheduleClassLoadService) {
        this.schoolService = schoolService;
        this.personService = personService;
        this.schoolClassService = schoolClassService;
        this.shiftTimeService = shiftTimeService;
        this.schoolCourseService = schoolCourseService;
        this.schedulePersonConstraintService = schedulePersonConstraintService;
        this.scheduleCourseConstraintService = scheduleCourseConstraintService;
        this.scheduleClassLoadService = scheduleClassLoadService;
    }

    @GetMapping("/school/{id}")
    public School getSchool(@PathVariable("id") Long id) {
        return schoolService.get(id);
    }

    @GetMapping("/class/school/{schoolId}")
    public List<SchoolClassDTO> listClassBySchool(@PathVariable("schoolId") Long schoolId) {
        return this.schoolClassService.listBySchool(schoolId);
    }

    @GetMapping("/shift-time/shift/{shiftId}")
    public List<ShiftTimeViewDTO> listShiftTimeByShift(@PathVariable("shiftId") Long shiftId) {
        return this.shiftTimeService.listByShiftInterface(shiftId);
    }

    @GetMapping("/instructor/school/{schoolId}")
    public List<PersonUserDto> listInstructorBySchool(@PathVariable("schoolId") Long id) {
        List<Person> personList = personService.findAllBySchoolAndRole(id, 4L);
        return personList.stream().map(this::toPersonUserDto).collect(Collectors.toList());
    }

    @GetMapping("/course/school/{schoolId}")
    public List<SchoolCourseDTO> listCourseBySchool(@PathVariable("schoolId") Long schoolId) {
        return this.schoolCourseService.findAllBySchool(schoolId);
    }

    @GetMapping("/instructor-constraint/shift/{shiftId}")
    public List<SchedulePersonConstraintDTO> listInstructorConstraintByShift(@PathVariable("shiftId") Long id) {
        return this.schedulePersonConstraintService.findAllByShift(id);
    }

    @GetMapping("/course-constraint/shift/{shiftId}")
    public List<ScheduleCourseConstraintDTO> listCourseConstraintByShift(@PathVariable("shiftId") Long id) {
        return this.scheduleCourseConstraintService.findAllByShift(id);
    }

    @GetMapping("/class-load/school/{schoolId}")
    public List<ScheduleClassLoadDTO> listClassLoadBySchool(@PathVariable("schoolId") Long id) {
        return this.scheduleClassLoadService.findAllBySchoolInterface(id);
    }

    private PersonUserDto toPersonUserDto(@NotNull Person person){
        PersonUserDto personAbstractDto = new PersonUserDto();
        personAbstractDto.setId(person.getId());
        personAbstractDto.setFirstName(person.getFirstName());
        personAbstractDto.setLastName(person.getLastName());
        personAbstractDto.setMiddleName(person.getMiddleName());
        return personAbstractDto;
    }
}
