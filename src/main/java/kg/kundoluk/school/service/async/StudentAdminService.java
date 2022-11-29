package kg.kundoluk.school.service.async;

import kg.kundoluk.school.dto.projection.StudentViewMobileDTO;
import kg.kundoluk.school.exception.AlreadyExistException;

import java.util.List;

public interface StudentAdminService {
    void changeLevel(Long schoolId, Long classId, Long chronicleId, Boolean isRaise) throws AlreadyExistException;
    List<StudentViewMobileDTO> getByClass(Long classId);
    void generateClassCourses(Long classId, Long chronicleId) throws AlreadyExistException;
}
