package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.projection.StudentViewMobileDTO;
import kg.kundoluk.school.endpoint.StudentMobileEndpoint;
import kg.kundoluk.school.service.student.StudentService;

import java.util.List;

@Endpoint
public class StudentMobileEndpointImpl implements StudentMobileEndpoint {
    private final StudentService studentService;

    public StudentMobileEndpointImpl(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public List<StudentViewMobileDTO> listByClass(Long classId) {
        return studentService.getStudentListWithParentByClass(classId);
    }
}
