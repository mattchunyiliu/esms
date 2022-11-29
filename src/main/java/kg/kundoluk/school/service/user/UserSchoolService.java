package kg.kundoluk.school.service.user;

import kg.kundoluk.school.dto.projection.UserSchoolDTO;
import kg.kundoluk.school.dto.user.UserSchoolRequest;
import kg.kundoluk.school.model.user.UserSchool;

import java.util.List;

public interface UserSchoolService {
    UserSchool create(UserSchoolRequest userSchoolRequest);
    void delete(Long id);
    void delete(Long userId, Long schoolId);
    List<UserSchoolDTO> getUserSchool(Long userId);
}
