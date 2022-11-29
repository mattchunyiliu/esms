package kg.kundoluk.school.service.student;

import kg.kundoluk.school.dto.student.StudentClassRequest;
import kg.kundoluk.school.model.student.StudentClass;

import java.util.List;

public interface StudentClassService {
    StudentClass create(StudentClassRequest studentClassRequest);
    void delete(Long id);
    List<StudentClass> findAllByStudent(Long studentId);
}
