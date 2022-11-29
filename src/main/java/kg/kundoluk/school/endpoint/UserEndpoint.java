package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.auth.AuthenticatedUserDto;
import kg.kundoluk.school.dto.projection.SchoolAdminViewDTO;
import kg.kundoluk.school.dto.projection.UserRayonDTO;
import kg.kundoluk.school.dto.user.*;
import kg.kundoluk.school.exception.AlreadyExistException;
import kg.kundoluk.school.exception.PermissionException;
import kg.kundoluk.school.exception.ResourceNotFoundException;
import kg.kundoluk.school.model.user.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserEndpoint {
    User createAdmin(AdminUserCreateRequest adminUserCreateRequest) throws AlreadyExistException;
    User createRayoner(RayonUserCreateRequest rayonUserCreateRequest) throws AlreadyExistException;
    List<UserRayonDTO> getUserRayonList(Long userId);
    List<SchoolAdminViewDTO> listAdmin();
    List<SchoolAdminViewDTO> listRayonerUser();
    User update(UserProfileUpdateRequest userDto);
    void uploadAvatar(User user, MultipartFile file);
    void setRoles(UserRoleRequest userRoleRequest) throws PermissionException;
    void switchRoles(UserSwitchRole userSwitchRole) throws PermissionException;
    UserProfileDto getUserByPhone(String phone) throws ResourceNotFoundException;
    UserProfileDto getUserByUsername(String username) throws ResourceNotFoundException;
    List<UserProfileDto> searchUser(UserSearch userSearch) throws ResourceNotFoundException;
    AuthenticatedUserDto getUserById(Long id);
    void delete(User user);
}
