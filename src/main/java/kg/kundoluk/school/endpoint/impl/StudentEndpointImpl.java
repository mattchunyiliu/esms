package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.dto.person.PersonAbstractDto;
import kg.kundoluk.school.dto.projection.StudentViewDTO;
import kg.kundoluk.school.dto.projection.StudentViewMobileDTO;
import kg.kundoluk.school.dto.student.*;
import kg.kundoluk.school.dto.user.UserRequest;
import kg.kundoluk.school.dto.user.UserSchoolRequest;
import kg.kundoluk.school.dto.user.UserSearch;
import kg.kundoluk.school.endpoint.StudentEndpoint;
import kg.kundoluk.school.exception.AlreadyExistException;
import kg.kundoluk.school.exception.ConstraintMappingException;
import kg.kundoluk.school.model.*;
import kg.kundoluk.school.model.enums.Gender;
import kg.kundoluk.school.model.enums.SubscriptionType;
import kg.kundoluk.school.model.references.ChronicleYear;
import kg.kundoluk.school.model.references.Role;
import kg.kundoluk.school.model.school.School;
import kg.kundoluk.school.model.school.SchoolClass;
import kg.kundoluk.school.model.student.Student;
import kg.kundoluk.school.model.user.User;
import kg.kundoluk.school.repository.RoleRepository;
import kg.kundoluk.school.repository.SchoolClassRepository;
import kg.kundoluk.school.repository.SchoolRepository;
import kg.kundoluk.school.service.references.ChronicleService;
import kg.kundoluk.school.service.async.StudentCourseGenerateService;
import kg.kundoluk.school.service.student.StudentClassService;
import kg.kundoluk.school.service.student.StudentService;
import kg.kundoluk.school.service.user.UserSchoolService;
import kg.kundoluk.school.service.user.UserService;
import kg.kundoluk.school.utils.TimeHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentEndpointImpl implements StudentEndpoint {
    private final StudentService service;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserSchoolService userSchoolService;
    private final SchoolRepository schoolRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final StudentClassService studentClassService;
    private final ChronicleService chronicleService;
    private final StudentCourseGenerateService studentCourseGenerateService;

    public StudentEndpointImpl(StudentService service, UserService userService, RoleRepository roleRepository, UserSchoolService userSchoolService, SchoolRepository schoolRepository, SchoolClassRepository schoolClassRepository, StudentClassService studentClassService, ChronicleService chronicleService, StudentCourseGenerateService studentCourseGenerateService) {
        this.service = service;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userSchoolService = userSchoolService;
        this.schoolRepository = schoolRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.studentClassService = studentClassService;
        this.chronicleService = chronicleService;
        this.studentCourseGenerateService = studentCourseGenerateService;
    }

    @Override
    public Student create(StudentCreateRequest studentCreateRequest) throws AlreadyExistException {

        LocalDate birthday = TimeHelper.getDateOfBirth(studentCreateRequest.getDateOfBirth());
        Gender gender = studentCreateRequest.getGender()==null?Gender.FEMALE:Gender.values()[studentCreateRequest.getGender()];

        // CHECK STUDENT
        /*if (!StringUtils.isEmpty(studentCreateRequest.getPhone()) && userService.existByPhone(studentCreateRequest.getPhone())){

            user = userService.findByPhone(studentCreateRequest.getPhone());
            if (user.getRoles().stream().anyMatch(r->r.getCode().equalsIgnoreCase("ROLE_STUDENT")))
                throw new AlreadyExistException(String.format("STUDENT_PHONE_EXISTS %s",studentCreateRequest.getPhone()));

        } else {
            String studentTitle = service.getStudentSchoolByTitle(studentCreateRequest.getFirstName(), studentCreateRequest.getLastName(), studentCreateRequest.getMiddleName(), gender.ordinal(), birthday);
            if (!StringUtils.isEmpty(studentTitle))
                throw new AlreadyExistException(studentTitle);
        }*/

        // CHECK STUDENT
        StudentViewMobileDTO studentTitle = service.getStudentSchoolByTitle(studentCreateRequest.getFirstName(), studentCreateRequest.getLastName(), studentCreateRequest.getMiddleName(), gender.ordinal(), birthday);
        if (studentTitle != null) {
            if (studentTitle.getArchived())
                service.archive(studentTitle.getId(), false);
            throw new AlreadyExistException(String.format("%s %s : %s",studentTitle.getLastName(),studentTitle.getFirstName(),studentTitle.getMiddleName()));
        }

        SchoolClass schoolClass = schoolClassRepository.getOne(studentCreateRequest.getClassId());

        // CREATE AUTH USER
        /*if (user == null || user.getRoles().stream().noneMatch(r->r.getCode().equalsIgnoreCase("ROLE_STUDENT")))
            user = createUser(studentCreateRequest);*/

        User user = createUser(studentCreateRequest);

        // CREATE USER SCHOOL
        createUserSchool(user, studentCreateRequest.getSchoolId());

        StudentRequest studentRequest = StudentRequest.builder()
                .firstName(studentCreateRequest.getFirstName())
                .lastName(studentCreateRequest.getLastName())
                .middleName(studentCreateRequest.getMiddleName())
                .user(user)
                .archived(false)
                .gender(gender)
                .schoolClass(schoolClass)
                .nationality(studentCreateRequest.getNationality())
                .birthday(birthday)
                .subscriptionType(SubscriptionType.FREE)
                .build();

        Student student = service.create(studentRequest);

        // CREATE STUDENT CLASS
        ChronicleYear chronicleYear = chronicleService.getActive();
        createStudentClass(student, schoolClass, chronicleYear);

        // CREATE STUDENT COURSES FROM SCHEDULE
        studentCourseGenerateService.generateStudentCourse(student.getId(), studentCreateRequest.getClassId());

        return student;
    }

    @Override
    public Student edit(StudentUpdateRequest studentUpdateRequest, Long id) {
        Student student = service.findById(id);
        StudentRequest studentRequest = StudentRequest.builder()
                .firstName(studentUpdateRequest.getFirstName())
                .lastName(studentUpdateRequest.getLastName())
                .middleName(studentUpdateRequest.getMiddleName())
                .archived(studentUpdateRequest.getArchived())
                .nationality(studentUpdateRequest.getNationality())
                .gender(Gender.values()[studentUpdateRequest.getGender()])
                .schoolClass(studentUpdateRequest.getClassId()!=null?schoolClassRepository.getOne(studentUpdateRequest.getClassId()):null)
                .birthday(TimeHelper.getDateOfBirth(studentUpdateRequest.getDateOfBirth()))
                .build();

        // UPDATE USER
        updateUser(studentUpdateRequest, student.getUser());

        return service.edit(studentRequest, student);
    }

    @Override
    public void editClass(StudentClassUpdateRequest studentClassUpdateRequest) {
        Set<Student> students = service.findAllByIdList(studentClassUpdateRequest.getStudentList());
        SchoolClass schoolClass = schoolClassRepository.getOne(studentClassUpdateRequest.getClassId());

        for (Student student: students){
            StudentRequest studentRequest = StudentRequest.builder()
                    .schoolClass(schoolClass)
                    .archived(studentClassUpdateRequest.getArchived())
                    .build();
            service.edit(studentRequest, student);
            // CREATE STUDENT CLASS
            ChronicleYear chronicleYear = chronicleService.getActive();
            createStudentClass(student, schoolClass, chronicleYear);
        }
    }

    @Override
    public StudentUserResponse findById(Long id) {
        return toStudentUserDto(service.findById(id));
    }

    @Override
    public StudentUserResponse findByUserId(Long userId) {
        Student student = service.findByUserId(userId);
        StudentUserResponse response = toStudentUserDto(student);
        response.setSchoolId(student.getSchoolClass().getSchool().getId());
        response.setSchoolTitle(student.getSchoolClass().getSchool().getName());
        return response;
    }

    @Override
    public Boolean delete(Student student) throws ConstraintMappingException {
        return service.delete(student);
    }

    @Override
    public List<StudentViewDTO> studentListBySchool(Long schoolId, Boolean archived) {
        return service.getStudentListBySchool(schoolId, archived);
    }

    @Override
    public List<StudentViewMobileDTO> studentListByClass(Long classId) {
        return service.getStudentListByClass(classId);
    }

    @Override
    public List<StudentSearchResult> searchStudent(UserSearch userSearch) {
        String middleName = "-";
        if (!StringUtils.isEmpty(userSearch.getMiddleName()))
            middleName = userSearch.getMiddleName();
        List<Student> students = service.searchStudent(userSearch.getFirstName().toLowerCase(), userSearch.getLastName().toLowerCase(), middleName.toLowerCase());
        return students.stream().map(this::toStudentSearchResult).collect(Collectors.toList());
    }

    private User createUser(StudentCreateRequest studentCreateRequest){
        Set<Role> roles = Collections.singleton(roleRepository.findByCode("ROLE_STUDENT"));

        String username = userService.generateUsername(studentCreateRequest.getSchoolId(), roles.stream().findFirst().get().getCode());

        UserRequest userRequest = UserRequest.builder()
                .firstName(studentCreateRequest.getFirstName())
                .lastName(studentCreateRequest.getLastName())
                .middleName(studentCreateRequest.getMiddleName())
                .phone(studentCreateRequest.getPhone())
                .roles(roles)
                .username(username)
                .password(username)
                .build();
        return userService.create(userRequest);
    }

    private void updateUser(StudentUpdateRequest studentUpdateRequest, User user){

        UserRequest userRequest = UserRequest.builder()
                .firstName(studentUpdateRequest.getFirstName())
                .lastName(studentUpdateRequest.getLastName())
                .middleName(studentUpdateRequest.getMiddleName())
                .phone(studentUpdateRequest.getPhone())
                .build();
        userService.update(userRequest, user);

    }

    private void createUserSchool(User user, Long schoolId){
        School school = schoolRepository.getOne(schoolId);
        UserSchoolRequest userSchoolRequest = UserSchoolRequest.builder().school(school).user(user).active(true).build();
        userSchoolService.create(userSchoolRequest);
    }

    private void createStudentClass(Student student, SchoolClass schoolClass, ChronicleYear chronicleYear){
        StudentClassRequest studentClassRequest = StudentClassRequest.builder()
                .schoolClass(schoolClass)
                .student(student)
                .chronicleYear(chronicleYear)
                .build();

        studentClassService.create(studentClassRequest);
    }

    private StudentUserResponse toStudentUserDto(Student student){

        StudentUserResponse studentUserResponse = new StudentUserResponse();
        studentUserResponse.setId(student.getId());
        studentUserResponse.setAvatar(student.getUser().getAvatar());
        studentUserResponse.setUserId(student.getUser().getId());
        studentUserResponse.setUsername(student.getUser().getUsername());
        studentUserResponse.setClassId(student.getSchoolClass().getId());
        studentUserResponse.setClassTitle(student.getSchoolClass().getSelectorTitle());
        studentUserResponse.setFirstName(student.getFirstName());
        studentUserResponse.setLastName(student.getLastName());
        studentUserResponse.setMiddleName(student.getMiddleName());
        studentUserResponse.setGender(student.getGender().ordinal());
        studentUserResponse.setPhone(student.getUser().getPhone());
        studentUserResponse.setNationality(student.getNationality());
        studentUserResponse.setSubscriptionType(student.getSubscriptionType());
        if (student.getBirthday()!=null)
            studentUserResponse.setDateOfBirth(TimeHelper.DATE_REVERSE_FORMATTER.format(student.getBirthday()));

        return studentUserResponse;
    }

    private StudentSearchResult toStudentSearchResult(Student student){
        StudentSearchResult studentSearchResult = new StudentSearchResult(student);
        if (student.getParents()!=null){
            studentSearchResult.setParents(student.getParents().stream().map(studentParent -> toPersonAbstract(studentParent.getParent())).collect(Collectors.toList()));
        }
        return studentSearchResult;
    }

    private PersonAbstractDto toPersonAbstract(Person person){

        PersonAbstractDto personAbstractDto = new PersonAbstractDto();
        personAbstractDto.setFirstName(person.getFirstName());
        personAbstractDto.setLastName(person.getLastName());
        personAbstractDto.setMiddleName(person.getMiddleName());
        personAbstractDto.setPhone(person.getPhone());

        return personAbstractDto;
    }
}
