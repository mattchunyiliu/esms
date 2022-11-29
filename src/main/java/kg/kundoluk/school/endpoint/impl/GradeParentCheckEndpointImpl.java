package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.projection.GradeParentCheckResponse;
import kg.kundoluk.school.dto.student.parent.GradeParentCheckCreateRequest;
import kg.kundoluk.school.dto.student.parent.GradeParentCheckRequest;
import kg.kundoluk.school.endpoint.GradeParentCheckEndpoint;
import kg.kundoluk.school.repository.StudentRepository;
import kg.kundoluk.school.repository.UserRepository;
import kg.kundoluk.school.service.grade.GradeParentCheckService;
import kg.kundoluk.school.utils.TimeHelper;

import java.time.LocalDate;
import java.util.List;

@Endpoint
public class GradeParentCheckEndpointImpl implements GradeParentCheckEndpoint {
    private final GradeParentCheckService gradeParentCheckService;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public GradeParentCheckEndpointImpl(GradeParentCheckService gradeParentCheckService, StudentRepository studentRepository, UserRepository userRepository) {
        this.gradeParentCheckService = gradeParentCheckService;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void create(GradeParentCheckCreateRequest gradeParentCheckCreateRequest) {

        GradeParentCheckRequest gradeParentCheckRequest = GradeParentCheckRequest.builder()
                .checked(gradeParentCheckCreateRequest.getCheck())
                .startDate(LocalDate.parse(gradeParentCheckCreateRequest.getStartDate(), TimeHelper.DATE_REVERSE_FORMATTER))
                .endDate(LocalDate.parse(gradeParentCheckCreateRequest.getEndDate(), TimeHelper.DATE_REVERSE_FORMATTER))
                .student(studentRepository.getOne(gradeParentCheckCreateRequest.getStudentId()))
                .user(userRepository.getOne(gradeParentCheckCreateRequest.getUserId()))
                .weekNumber(gradeParentCheckCreateRequest.getWeekNumber())
                .build();

        gradeParentCheckService.create(gradeParentCheckRequest);
    }

    @Override
    public List<GradeParentCheckResponse> getList(Long studentId, Long classId, String startDate, String endDate) {
        if (studentId == null)
            studentId = 0L;
        LocalDate start = LocalDate.parse(startDate, TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate end = LocalDate.parse(endDate, TimeHelper.DATE_REVERSE_FORMATTER);
        return gradeParentCheckService.getList(studentId, classId, start, end);
    }
}
