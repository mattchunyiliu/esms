package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.projection.FirebaseTokenDTO;
import kg.kundoluk.school.dto.student.parent.StudentParentBulkDto;
import kg.kundoluk.school.dto.student.parent.StudentParentCreateRequest;
import kg.kundoluk.school.dto.student.parent.StudentParentUserResponse;
import kg.kundoluk.school.dto.student.StudentUserResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface StudentParentEndpoint {
    void create(StudentParentCreateRequest studentParentCreateRequest);
    List<StudentParentUserResponse> getByStudent(Long studentId);
    List<StudentParentUserResponse> getByClass(Long classId);
    List<StudentUserResponse> getByPerson(Long personId);
    List<StudentParentUserResponse> getByPersonUsername(String personUsername);
    List<FirebaseTokenDTO> findParentTokensByStudent(Long studentId);
    void delete(Long studentParentId);
    void substituteStudent(StudentParentBulkDto studentParentBulkDto, Authentication authentication);
}
