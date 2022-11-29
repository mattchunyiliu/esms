package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.projection.StudentCourseViewDTO;
import kg.kundoluk.school.dto.report.ClassInstructorCourseDTO;
import kg.kundoluk.school.dto.student.courses.StudentCourseCreateRequest;
import kg.kundoluk.school.dto.student.courses.StudentCourseDto;
import kg.kundoluk.school.dto.student.courses.StudentCourseMobileResponse;
import kg.kundoluk.school.dto.student.courses.StudentCourseResponse;
import kg.kundoluk.school.exception.AlreadyExistException;
import kg.kundoluk.school.exception.ConstraintMappingException;
import kg.kundoluk.school.model.student.StudentCourse;

import java.util.List;

public interface StudentCourseEndpoint {
    StudentCourse create(StudentCourseCreateRequest studentCourseCreateRequest);
    void createBulk(List<StudentCourseCreateRequest> studentCourseCreateRequestList) throws AlreadyExistException;
    void deleteBulk(StudentCourseCreateRequest studentCourseCreateRequest);
    List<StudentCourseResponse> findAllByStudent(Long studentId, Long chronicleId);
    List<StudentCourseResponse> findAllByClass(Long classId, Long chronicleId);
    List<StudentCourseMobileResponse> findAllByStudentMobile(Long studentId, Long chronicleId);
    List<StudentCourseViewDTO> findAllByPersonCourseClassInterface(Long personId, Long courseId, Long classId, Long chronicleId);
    void delete(Long id) throws ConstraintMappingException;
    List<ClassInstructorCourseDTO> getClassCourseList(Long classId, Long chronicleId);
}
