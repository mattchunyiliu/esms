package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.projection.UserSchoolDTO;
import kg.kundoluk.school.dto.user.UserSchoolCreateRequest;

import java.util.List;

public interface UserSchoolEndpoint {
    void create(UserSchoolCreateRequest userSchoolCreateRequest);
    void delete(Long id);
    List<UserSchoolDTO> getUserSchool(Long userId);
}
