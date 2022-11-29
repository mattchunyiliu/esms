package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.projection.StudentViewMobileDTO;

import java.util.List;

public interface StudentMobileEndpoint {
    List<StudentViewMobileDTO> listByClass(Long classId);
}
