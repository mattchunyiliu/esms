package kg.kundoluk.school.service.student;

import kg.kundoluk.school.dto.projection.FirebaseTokenDTO;
import kg.kundoluk.school.dto.student.parent.StudentParentDto;
import kg.kundoluk.school.dto.student.parent.StudentParentRequest;
import kg.kundoluk.school.model.student.StudentParent;

import java.util.List;

public interface StudentParentService {
    StudentParent create(StudentParentRequest studentParentRequest);
    StudentParent edit(StudentParentRequest studentParentRequest, StudentParent studentParent);
    void modifySQL(StudentParentDto studentParentDto);
    StudentParent findById(Long id);
    List<StudentParent> findAllByStudent(Long studentId);
    List<StudentParent> findAllByClass(Long classId);
    List<StudentParent> findAllByParent(Long parentId);
    List<StudentParent> findAllByParentUsername(String username);
    List<FirebaseTokenDTO> findParentTokensByStudent(Long studentId);
    List<FirebaseTokenDTO> findParentTokensByClass(Long classId);
    void delete(Long id);
}
