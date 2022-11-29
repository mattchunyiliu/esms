package kg.kundoluk.school.service.student;

import kg.kundoluk.school.dto.projection.StudentViewDTO;
import kg.kundoluk.school.dto.projection.StudentViewMobileDTO;
import kg.kundoluk.school.dto.student.StudentRequest;
import kg.kundoluk.school.exception.ConstraintMappingException;
import kg.kundoluk.school.model.student.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface StudentService {
    Student create(StudentRequest studentRequest);
    Student edit(StudentRequest studentRequest, Student student);
    Student findById(Long id);
    StudentViewDTO findViewById(Long id);
    Student findByUserId(Long id);
    void archive(Long studentId, Boolean status);
    StudentViewMobileDTO getStudentSchoolByTitle(String firstName, String lastName, String middleName, Integer gender, LocalDate dob);
    Boolean delete(Student student) throws ConstraintMappingException;
    List<StudentViewDTO> getStudentListBySchool(Long schoolId, Boolean archived);
    List<StudentViewMobileDTO> getStudentListByClass(Long classId);
    List<StudentViewMobileDTO> getStudentListWithParentByClass(Long classId);
    Set<Student> findAllByClass(Long classId);
    Set<Student> findAllBySchool(Long schoolId);
    Set<Student> findAllByIdList(List<Long> ids);
    List<Student> searchStudent(String firstName, String lastName, String middleName);
}
