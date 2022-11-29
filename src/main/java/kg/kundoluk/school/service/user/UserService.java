package kg.kundoluk.school.service.user;

import kg.kundoluk.school.dto.projection.SchoolAdminViewDTO;
import kg.kundoluk.school.dto.projection.UserRayonDTO;
import kg.kundoluk.school.dto.user.UserProfileUpdateRequest;
import kg.kundoluk.school.dto.user.UserRequest;
import kg.kundoluk.school.dto.user.UserSearch;
import kg.kundoluk.school.model.user.User;

import java.util.List;

public interface UserService {

    User findByPhone(String phone);
    User findById(Long id);
    User getOne(Long id);
    List<User> findAllBySchoolAndRole(Long schoolId, Long roleId);
    boolean existByPhone(String phone);
    boolean existByUsername(String username);
    User findByUsername(String username);
    User getByUsername(String username);
    User create(UserRequest userRequest);
    User update(UserRequest userRequest, User user);
    User update(UserProfileUpdateRequest userDto);
    void save(User user);
    void delete(User user);
    String generateUsername(Long schoolId, String roleCode);
    List<User> searchUser(UserSearch userSearch);
    List<SchoolAdminViewDTO> listSchoolAdmin();
    List<SchoolAdminViewDTO> listRayonAdmin();
    List<UserRayonDTO> getUserRayons(Long userId);
}
