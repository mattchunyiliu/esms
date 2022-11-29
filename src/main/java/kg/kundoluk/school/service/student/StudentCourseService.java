package kg.kundoluk.school.service.student;

import kg.kundoluk.school.dto.projection.StudentCourseViewDTO;
import kg.kundoluk.school.dto.report.ClassInstructorCourseDTO;
import kg.kundoluk.school.dto.student.courses.StudentCourseCreateRequest;
import kg.kundoluk.school.dto.student.courses.StudentCourseRequest;
import kg.kundoluk.school.exception.AlreadyExistException;
import kg.kundoluk.school.exception.ConstraintMappingException;
import kg.kundoluk.school.model.student.StudentCourse;

import java.util.List;

public interface StudentCourseService {
    StudentCourse create(StudentCourseRequest studentCourseRequest);
    void createBulk(List<StudentCourseRequest> studentCourseRequestList) throws AlreadyExistException;
    void deleteBulk(StudentCourseCreateRequest studentCourseCreateRequest);
    List<StudentCourse> findAllByStudent(Long studentId, Long chronicleId);
    List<StudentCourse> findAllByClass(Long classId, Long chronicleId);
    List<StudentCourseViewDTO> findAllByPersonClassChronicleInterface(Long personId, Long courseId, Long classId, Long chronicleId);
    StudentCourse findById(Long id);
    void delete(Long id) throws ConstraintMappingException;
    List<ClassInstructorCourseDTO> getClassCourseList(Long classId, Long chronicleId);
}
