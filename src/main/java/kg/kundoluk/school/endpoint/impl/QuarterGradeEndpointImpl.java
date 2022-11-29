package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.grade.*;
import kg.kundoluk.school.dto.projection.QuarterGradeDTO;
import kg.kundoluk.school.endpoint.QuarterGradeEndpoint;
import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.enums.QuarterGradeMarkType;
import kg.kundoluk.school.model.grade.QuarterGrade;
import kg.kundoluk.school.model.school.Quarter;
import kg.kundoluk.school.model.school.SchoolClass;
import kg.kundoluk.school.model.student.StudentCourse;
import kg.kundoluk.school.repository.PersonRepository;
import kg.kundoluk.school.repository.QuarterRepository;
import kg.kundoluk.school.repository.SchoolClassRepository;
import kg.kundoluk.school.repository.StudentCourseRepository;
import kg.kundoluk.school.service.grade.QuarterGradeService;
import kg.kundoluk.school.service.rabbit.RabbitMessageProducer;

import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class QuarterGradeEndpointImpl implements QuarterGradeEndpoint {
    private final QuarterGradeService quarterGradeService;
    private final PersonRepository personRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final QuarterRepository quarterRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final RabbitMessageProducer rabbitMessageProducer;

    public QuarterGradeEndpointImpl(QuarterGradeService quarterGradeService, PersonRepository personRepository, StudentCourseRepository studentCourseRepository, QuarterRepository quarterRepository, SchoolClassRepository schoolClassRepository, RabbitMessageProducer rabbitMessageProducer) {
        this.quarterGradeService = quarterGradeService;
        this.personRepository = personRepository;
        this.studentCourseRepository = studentCourseRepository;
        this.quarterRepository = quarterRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.rabbitMessageProducer = rabbitMessageProducer;
    }

    @Override
    public QuarterGrade create(QuarterGradeCreateRequest quarterGradeCreateRequest) {
        QuarterGrade quarterGrade = quarterGradeService.create(toQuarterGradeRequest(quarterGradeCreateRequest));
        rabbitMessageProducer.sendQuarterGradeNotification(quarterGradeCreateRequest);
        return quarterGrade;
    }

    @Override
    public QuarterGrade edit(QuarterGradeUpdateRequest quarterGradeUpdateRequest, QuarterGrade quarterGrade) {
        QuarterGradeRequest quarterGradeRequest = QuarterGradeRequest.builder()
                .gradeMarkType(quarterGradeUpdateRequest.getGradeMarkType())
                .mark(quarterGradeUpdateRequest.getMark())
                .build();
        return quarterGradeService.edit(quarterGradeRequest, quarterGrade);
    }

    @Override
    public void delete(Long id) {
        quarterGradeService.deleteById(id);
    }

    @Override
    public List<QuarterGradeMobileResponse> findAllByChronicle(QuarterGradeMobileResponseRequest gradeMobileCreateDayRequest) {
        Long personId = gradeMobileCreateDayRequest.getPersonId();
        Long courseId = gradeMobileCreateDayRequest.getCourseId();
        Long classId = gradeMobileCreateDayRequest.getClassId();
        Long chronicleId = gradeMobileCreateDayRequest.getChronicleId();

        List<QuarterGradeDTO> grades = quarterGradeService.list(personId, courseId, classId, chronicleId);
        return grades.stream().map(this::toGradeMobileResponse).collect(Collectors.toList());
    }

    @Override
    public List<QuarterGradeMobileResponse> findAllByStudentChronicle(Long studentId, Long chronicleId) {
        return quarterGradeService.listByStudent(studentId, chronicleId).stream().map(this::toGradeMobileResponse).collect(Collectors.toList());
    }

    private QuarterGradeRequest toQuarterGradeRequest(QuarterGradeCreateRequest quarterGradeCreateRequest){
        Person person = personRepository.getOne(quarterGradeCreateRequest.getPersonId());
        StudentCourse studentCourse = studentCourseRepository.getOne(quarterGradeCreateRequest.getStudentCourseId());
        Quarter quarter = quarterRepository.getOne(quarterGradeCreateRequest.getQuarterId());
        SchoolClass schoolClass = schoolClassRepository.getOne(quarterGradeCreateRequest.getClassId());

        return QuarterGradeRequest.builder()
                .person(person)
                .studentCourse(studentCourse)
                .schoolClass(schoolClass)
                .gradeMarkType(quarterGradeCreateRequest.getGradeMarkType())
                .mark(quarterGradeCreateRequest.getMark())
                .quarter(quarter)
                .build();
    }

    private QuarterGradeMobileResponse toGradeMobileResponse(QuarterGradeDTO grade){
        QuarterGradeMobileResponse gradeMobileResponse = new QuarterGradeMobileResponse();
        gradeMobileResponse.setGradeId(grade.getGradeId());
        gradeMobileResponse.setGradeMarkType(QuarterGradeMarkType.values()[grade.getGradeMarkType()]);
        gradeMobileResponse.setStudentCourseId(grade.getStudentCourseId());
        gradeMobileResponse.setMark(grade.getMark());
        gradeMobileResponse.setQuarterId(grade.getQuarterId());
        return gradeMobileResponse;
    }
}
