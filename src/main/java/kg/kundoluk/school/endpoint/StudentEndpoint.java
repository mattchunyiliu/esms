package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.projection.StudentViewDTO;
import kg.kundoluk.school.dto.projection.StudentViewMobileDTO;
import kg.kundoluk.school.dto.student.*;
import kg.kundoluk.school.dto.user.UserSearch;
import kg.kundoluk.school.exception.AlreadyExistException;
import kg.kundoluk.school.exception.ConstraintMappingException;
import kg.kundoluk.school.model.student.Student;

import java.util.List;

public interface StudentEndpoint {
    Student create(StudentCreateRequest studentCreateRequest) throws AlreadyExistException;
    Student edit(StudentUpdateRequest studentUpdateRequest, Long id);
    void editClass(StudentClassUpdateRequest studentClassUpdateRequest);
    StudentUserResponse findById(Long id);
    StudentUserResponse findByUserId(Long userId);
    Boolean delete(Student student) throws ConstraintMappingException;
    List<StudentViewDTO> studentListBySchool(Long schoolId, Boolean archived);
    List<StudentViewMobileDTO> studentListByClass(Long classId);
    List<StudentSearchResult> searchStudent(UserSearch userSearch);
}
