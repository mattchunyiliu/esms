package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.projection.StudentCourseViewDTO;
import kg.kundoluk.school.dto.report.ClassInstructorCourseDTO;
import kg.kundoluk.school.dto.student.courses.*;
import kg.kundoluk.school.endpoint.StudentCourseEndpoint;
import kg.kundoluk.school.exception.AlreadyExistException;
import kg.kundoluk.school.exception.ConstraintMappingException;
import kg.kundoluk.school.model.school.SchoolCourse;
import kg.kundoluk.school.model.student.StudentCourse;
import kg.kundoluk.school.repository.*;
import kg.kundoluk.school.service.student.StudentCourseService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class StudentCourseEndpointImpl implements StudentCourseEndpoint {
    private final StudentCourseService studentCourseService;
    private final StudentRepository studentRepository;
    private final SchoolCourseRepository schoolCourseRepository;
    private final PersonRepository personRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final ChronicleRepository chronicleRepository;

    public StudentCourseEndpointImpl(StudentCourseService studentCourseService, StudentRepository studentRepository, SchoolCourseRepository schoolCourseRepository, PersonRepository personRepository, SchoolClassRepository schoolClassRepository, ChronicleRepository chronicleRepository) {
        this.studentCourseService = studentCourseService;
        this.studentRepository = studentRepository;
        this.schoolCourseRepository = schoolCourseRepository;
        this.personRepository = personRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.chronicleRepository = chronicleRepository;
    }

    @Override
    public StudentCourse create(StudentCourseCreateRequest studentCourseCreateRequest) {
        StudentCourseRequest studentCourseRequest = StudentCourseRequest.builder()
                .chronicleYear(chronicleRepository.getOne(studentCourseCreateRequest.getChronicleId()))
                .person(personRepository.getOne(studentCourseCreateRequest.getPersonId()))
                .schoolClass(schoolClassRepository.getOne(studentCourseCreateRequest.getClassId()))
                .schoolCourse(schoolCourseRepository.getOne(studentCourseCreateRequest.getCourseId()))
                .student(studentRepository.getOne(studentCourseCreateRequest.getStudentId()))
                .build();
        return studentCourseService.create(studentCourseRequest);
    }

    @Override
    public void createBulk(List<StudentCourseCreateRequest> studentCourseCreateRequestList) throws AlreadyExistException {
        List<StudentCourseRequest> studentCourseRequests = new ArrayList<>();
        for (StudentCourseCreateRequest studentCourseCreateRequest: studentCourseCreateRequestList){
            studentCourseRequests.add(StudentCourseRequest.builder()
                    .chronicleYear(chronicleRepository.getOne(studentCourseCreateRequest.getChronicleId()))
                    .person(personRepository.getOne(studentCourseCreateRequest.getPersonId()))
                    .schoolClass(schoolClassRepository.getOne(studentCourseCreateRequest.getClassId()))
                    .schoolCourse(schoolCourseRepository.getOne(studentCourseCreateRequest.getCourseId()))
                    .student(studentRepository.getOne(studentCourseCreateRequest.getStudentId()))
                    .build());
        }
        studentCourseService.createBulk(studentCourseRequests);
    }

    @Override
    public void deleteBulk(StudentCourseCreateRequest studentCourseCreateRequest) {
        studentCourseService.deleteBulk(studentCourseCreateRequest);
    }

    @Override
    public List<StudentCourseResponse> findAllByStudent(Long studentId, Long chronicleId) {
        return studentCourseService.findAllByStudent(studentId, chronicleId).stream().map(this::toStudentCourseResponse).collect(Collectors.toList());
    }

    @Override
    public List<StudentCourseResponse> findAllByClass(Long classId, Long chronicleId) {
        return studentCourseService.findAllByClass(classId, chronicleId).stream().map(this::toStudentCourseResponse).collect(Collectors.toList());
    }

    @Override
    public List<StudentCourseMobileResponse> findAllByStudentMobile(Long studentId, Long chronicleId) {
        return studentCourseService.findAllByStudent(studentId, chronicleId).stream().map(this::studentCourseMobileResponse).collect(Collectors.toList());
    }

    @Override
    public List<StudentCourseViewDTO> findAllByPersonCourseClassInterface(Long personId, Long courseId, Long classId, Long chronicleId) {
        return studentCourseService.findAllByPersonClassChronicleInterface(personId, courseId, classId, chronicleId);
    }

    @Override
    public void delete(Long id) throws ConstraintMappingException {
        studentCourseService.delete(id);
    }

    @Override
    public List<ClassInstructorCourseDTO> getClassCourseList(Long classId, Long chronicleId) {
        return studentCourseService.getClassCourseList(classId, chronicleId);
    }

    private StudentCourseResponse toStudentCourseResponse(StudentCourse studentCourse){
        StudentCourseResponse studentCourseResponse = new StudentCourseResponse();
        studentCourseResponse.setClassId(studentCourse.getSchoolClass().getId());
        studentCourseResponse.setCourseId(studentCourse.getSchoolCourse().getId());
        studentCourseResponse.setInstructorId(studentCourse.getPerson().getId());
        studentCourseResponse.setId(studentCourse.getId());
        return studentCourseResponse;
    }

    private StudentCourseMobileResponse studentCourseMobileResponse(StudentCourse studentCourse){
        StudentCourseMobileResponse response = new StudentCourseMobileResponse();
        response.setClassId(studentCourse.getSchoolClass().getId());
        response.setStudentCourseId(studentCourse.getId());
        response.setInstructorId(studentCourse.getPerson().getId());
        response.setInstructorTitle(studentCourse.getPerson().getSelectorTitle());
        response.setInstructorUserId(studentCourse.getPerson().getUser().getId());

        SchoolCourse schoolCourse = studentCourse.getSchoolCourse();
        response.setSchoolCourseId(schoolCourse.getId());
        response.setCourseId(schoolCourse.getCourse().getId());
        response.setCourseTitle(schoolCourse.getCourse().getTitle());
        response.setCourseTitleKG(schoolCourse.getCourse().getTitleKg());
        response.setCourseTitleRU(schoolCourse.getCourse().getTitleRu());
        return response;
    }
}
