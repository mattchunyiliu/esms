package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.person.PersonCreateRequest;
import kg.kundoluk.school.dto.person.PersonDto;
import kg.kundoluk.school.dto.person.PersonRequest;
import kg.kundoluk.school.dto.projection.FirebaseTokenDTO;
import kg.kundoluk.school.dto.student.*;
import kg.kundoluk.school.dto.student.parent.*;
import kg.kundoluk.school.dto.user.UserRequest;
import kg.kundoluk.school.endpoint.PersonEndpoint;
import kg.kundoluk.school.endpoint.StudentParentEndpoint;
import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.school.School;
import kg.kundoluk.school.model.student.Student;
import kg.kundoluk.school.model.student.StudentParent;
import kg.kundoluk.school.repository.StudentRepository;
import kg.kundoluk.school.service.student.StudentParentService;
import kg.kundoluk.school.utils.TimeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class StudentParentEndpointImpl implements StudentParentEndpoint {
    private final StudentParentService studentParentService;
    private final StudentRepository studentRepository;
    private final PersonEndpoint personEndpoint;
    Logger logger = LoggerFactory.getLogger(StudentParentEndpointImpl.class);

    public StudentParentEndpointImpl(StudentParentService studentParentService, StudentRepository studentRepository, PersonEndpoint personEndpoint) {
        this.studentParentService = studentParentService;
        this.studentRepository = studentRepository;
        this.personEndpoint = personEndpoint;
    }

    @Override
    public void create(StudentParentCreateRequest studentParentCreateRequest) {

        Student student = studentRepository.getOne(studentParentCreateRequest.getStudentId());
        Person person = personEndpoint.create(toPersonCreateRequest(studentParentCreateRequest.getPersonDto()));
        StudentParentRequest studentParentRequest = StudentParentRequest.builder().person(person).student(student).parentalType(studentParentCreateRequest.getParentalType()).build();

        studentParentService.create(studentParentRequest);
    }

    @Override
    public List<StudentParentUserResponse> getByStudent(Long studentId) {
        List<StudentParent> studentParents = studentParentService.findAllByStudent(studentId);
        return studentParents.stream().map(this::toStudentParentUserResponse).collect(Collectors.toList());
    }

    @Override
    public List<StudentParentUserResponse> getByClass(Long classId) {
        List<StudentParent> studentParents = studentParentService.findAllByClass(classId);
        return studentParents.stream().map(this::toStudentParentUserResponse).collect(Collectors.toList());
    }

    @Override
    public List<StudentUserResponse> getByPerson(Long personId) {
        List<StudentParent> studentParents = studentParentService.findAllByParent(personId);
        return studentParents.stream().map(this::toStudentUserDto).collect(Collectors.toList());
    }

    @Override
    public List<StudentParentUserResponse> getByPersonUsername(String personUsername) {
        List<StudentParent> studentParents = studentParentService.findAllByParentUsername(personUsername);
        return studentParents.stream().map(this::toStudentParentUserResponse).collect(Collectors.toList());
    }

    @Override
    public List<FirebaseTokenDTO> findParentTokensByStudent(Long studentId) {
        return studentParentService.findParentTokensByStudent(studentId);
    }

    @Override
    public void delete(Long studentParentId) {
        studentParentService.delete(studentParentId);
    }

    @Override
    public void substituteStudent(StudentParentBulkDto studentParentBulkDto, Authentication authentication) {
        for (StudentParentDto studentParentDto: studentParentBulkDto.getParentStudentDtoList()){
            studentParentDto.setParentId(studentParentBulkDto.getParentId());
            studentParentDto.setParentalType(studentParentBulkDto.getParentalType());

            studentParentService.modifySQL(studentParentDto);
        }
    }

    private PersonCreateRequest toPersonCreateRequest(PersonDto personDto){
        PersonCreateRequest personCreateRequest = new PersonCreateRequest();
        personCreateRequest.setJob(personDto.getJob());
        personCreateRequest.setJobPlace(personDto.getJobPlace());
        personCreateRequest.setRoleId(personDto.getRoleId());
        personCreateRequest.setDateOfBirth(personDto.getDateOfBirth());
        personCreateRequest.setFirstName(personDto.getFirstName());
        personCreateRequest.setLastName(personDto.getLastName());
        personCreateRequest.setMiddleName(personDto.getMiddleName());
        personCreateRequest.setGender(personDto.getGender());
        personCreateRequest.setPhone(personDto.getPhone());

        return personCreateRequest;
    }

    private StudentParentUserResponse toStudentParentUserResponse(StudentParent studentParent){
        StudentParentUserResponse studentParentUserResponse = new StudentParentUserResponse();

        Person parent = studentParent.getParent();
        studentParentUserResponse.setId(parent.getId());
        studentParentUserResponse.setAvatar(parent.getUser().getAvatar());
        studentParentUserResponse.setUserId(parent.getUser().getId());
        studentParentUserResponse.setParentalType(studentParent.getParentalType());
        studentParentUserResponse.setStudentParentId(studentParent.getId());
        studentParentUserResponse.setUsername(parent.getUser().getUsername());
        studentParentUserResponse.setStudentId(studentParent.getStudent().getId());
        studentParentUserResponse.setStudentTitle(studentParent.getStudent().getSelectorTitle());

        if (parent.getBirthday()!=null)
            studentParentUserResponse.setDateOfBirth(TimeHelper.DATE_REVERSE_FORMATTER.format(parent.getBirthday()));
        studentParentUserResponse.setFirstName(parent.getFirstName());
        studentParentUserResponse.setLastName(parent.getLastName());
        studentParentUserResponse.setMiddleName(parent.getMiddleName());
        studentParentUserResponse.setGender(parent.getGender().ordinal());
        studentParentUserResponse.setPhone(parent.getPhone());
        studentParentUserResponse.setJob(parent.getJob());
        studentParentUserResponse.setJobPlace(parent.getJobPlace());

        return studentParentUserResponse;
    }

    private StudentUserResponse toStudentUserDto(StudentParent studentParent){

        Student student = studentParent.getStudent();

        StudentUserResponse studentUserResponse = new StudentUserResponse();
        studentUserResponse.setId(student.getId());
        studentUserResponse.setAvatar(student.getUser().getAvatar());
        studentUserResponse.setUserId(student.getUser().getId());
        studentUserResponse.setClassId(student.getSchoolClass().getId());
        studentUserResponse.setClassTitle(student.getSchoolClass().getSelectorTitle());
        studentUserResponse.setFirstName(student.getFirstName());
        studentUserResponse.setLastName(student.getLastName());
        studentUserResponse.setMiddleName(student.getMiddleName());
        studentUserResponse.setNationality(student.getNationality());
        studentUserResponse.setGender(student.getGender().ordinal());
        studentUserResponse.setPhone(student.getUser().getPhone());
        studentUserResponse.setSubscriptionType(student.getSubscriptionType());
        if (student.getBirthday()!=null)
            studentUserResponse.setDateOfBirth(TimeHelper.DATE_REVERSE_FORMATTER.format(student.getBirthday()));
        School school = student.getSchoolClass().getSchool();
        studentUserResponse.setSchoolId(school.getId());
        studentUserResponse.setSchoolTitle(school.getName());

        return studentUserResponse;
    }
}
