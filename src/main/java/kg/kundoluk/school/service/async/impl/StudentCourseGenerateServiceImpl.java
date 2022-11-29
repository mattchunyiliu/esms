package kg.kundoluk.school.service.async.impl;

import kg.kundoluk.school.dto.schedule.ScheduleCreateRequest;
import kg.kundoluk.school.dto.student.courses.StudentCourseRequest;
import kg.kundoluk.school.exception.AlreadyExistException;
import kg.kundoluk.school.model.references.ChronicleYear;
import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.schedule.Schedule;
import kg.kundoluk.school.model.schedule.ScheduleGroup;
import kg.kundoluk.school.model.school.SchoolClass;
import kg.kundoluk.school.model.school.SchoolCourse;
import kg.kundoluk.school.model.student.Student;
import kg.kundoluk.school.model.student.StudentCourse;
import kg.kundoluk.school.repository.ChronicleRepository;
import kg.kundoluk.school.repository.StudentRepository;
import kg.kundoluk.school.service.async.StudentCourseGenerateService;
import kg.kundoluk.school.service.schedule.ScheduleGroupService;
import kg.kundoluk.school.service.schedule.ScheduleService;
import kg.kundoluk.school.service.student.StudentCourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class StudentCourseGenerateServiceImpl implements StudentCourseGenerateService {
    private final ScheduleGroupService scheduleGroupService;
    private final ScheduleService scheduleService;
    private final StudentRepository studentRepository;
    private final ChronicleRepository chronicleRepository;
    private final StudentCourseService studentCourseService;

    public StudentCourseGenerateServiceImpl(ScheduleGroupService scheduleGroupService, ScheduleService scheduleService, StudentRepository studentRepository, ChronicleRepository chronicleRepository, StudentCourseService studentCourseService) {
        this.scheduleGroupService = scheduleGroupService;
        this.scheduleService = scheduleService;
        this.studentRepository = studentRepository;
        this.chronicleRepository = chronicleRepository;
        this.studentCourseService = studentCourseService;
    }

    @Async("asyncExecutor")
    @Override
    public void generateCourse(ScheduleCreateRequest scheduleCreateRequest, Person person, SchoolCourse schoolCourse, SchoolClass schoolClass) throws AlreadyExistException {
        log.info("Generating schedule courses for students");
        if (scheduleCreateRequest.getIsGroup()) {
            ScheduleGroup scheduleGroup = scheduleGroupService.findById(scheduleCreateRequest.getScheduleGroupId());
            createStudentCourses(person, schoolCourse, schoolClass, scheduleGroup.getStudents());

        } else {
            Set<Student> students = studentRepository.findAllBySchoolClassId(scheduleCreateRequest.getClassId());
            createStudentCourses(person, schoolCourse, schoolClass, students);
        }
    }

    @Async("asyncExecutor")
    @Override
    public void generateStudentCourse(Long studentId, Long classId) throws AlreadyExistException {

        ChronicleYear chronicleYear = chronicleRepository.getByActiveIsTrue();
        Student student = studentRepository.getOne(studentId);

        List<Schedule> schedules = scheduleService.listAllByClass(classId);
        List<StudentCourseRequest> studentCourseRequests = new ArrayList<>();

        List<StudentCourse> studentCourses = studentCourseService.findAllByStudent(studentId,chronicleYear.getId());

        List<Schedule> createdList = new ArrayList<>();
        for (Schedule schedule: schedules){
            if (createdList.stream()
                    .noneMatch(s->s.getPerson().equals(schedule.getPerson()) && s.getSchoolClass().equals(schedule.getSchoolClass()) && s.getSchoolCourse().equals(schedule.getSchoolCourse()))) {

                if (studentCourses.stream().noneMatch(sc->sc.getPerson().equals(schedule.getPerson()) &&
                        sc.getSchoolCourse().equals(schedule.getSchoolCourse()) &&
                        sc.getSchoolClass().equals(schedule.getSchoolClass()))) {

                    StudentCourseRequest studentCourseRequest = StudentCourseRequest.builder()
                            .chronicleYear(chronicleYear)
                            .student(student)
                            .person(schedule.getPerson())
                            .schoolClass(schedule.getSchoolClass())
                            .schoolCourse(schedule.getSchoolCourse())
                            .build();

                    //studentCourseService.create(studentCourseRequest);
                    studentCourseRequests.add(studentCourseRequest);

                    createdList.add(schedule);
                }
            }
        }
        studentCourseService.createBulk(studentCourseRequests);
    }

    public void createStudentCourses(Person person, SchoolCourse schoolCourse, SchoolClass schoolClass, Set<Student> students) throws AlreadyExistException {
        List<StudentCourseRequest> studentCourseRequests = new ArrayList<>();
        for (Student student : students) {
            StudentCourseRequest studentCourseRequest = StudentCourseRequest.builder()
                    .chronicleYear(chronicleRepository.getByActiveIsTrue())
                    .student(student)
                    .person(person)
                    .schoolClass(schoolClass)
                    .schoolCourse(schoolCourse)
                    .build();
            studentCourseRequests.add(studentCourseRequest);
        }
        studentCourseService.createBulk(studentCourseRequests);
    }
}
