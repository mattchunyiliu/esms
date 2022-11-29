package kg.kundoluk.school.controller.web.user;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.auth.AuthenticatedUserDto;
import kg.kundoluk.school.dto.projection.SchoolAdminViewDTO;
import kg.kundoluk.school.dto.projection.UserRayonDTO;
import kg.kundoluk.school.dto.user.*;
import kg.kundoluk.school.endpoint.UserEndpoint;
import kg.kundoluk.school.exception.AlreadyExistException;
import kg.kundoluk.school.exception.PermissionException;
import kg.kundoluk.school.exception.ResourceNotFoundException;
import kg.kundoluk.school.model.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/web/v1/user")
public class UserController {
    private final UserEndpoint userEndpoint;

    public UserController(UserEndpoint userEndpoint) {
        this.userEndpoint = userEndpoint;
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/edit/profile")
    public ResponseEntity<?> editProfile(
            @Valid @RequestBody UserProfileUpdateRequest userDto
    ) {
        userEndpoint.update(userDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/phone")
    public UserProfileDto getUserByPhone(
            @RequestParam("phone") String phone
    ) throws ResourceNotFoundException {
        return userEndpoint.getUserByPhone(phone);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/username")
    public UserProfileDto getUserByUsername(
            @RequestParam("username") String username
    ) throws ResourceNotFoundException {
        return userEndpoint.getUserByUsername(username);
    }

    @GetMapping("/{id}")
    public AuthenticatedUserDto getUserById(
            @PathVariable("id") Long id
    ) {
        return userEndpoint.getUserById(id);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/admin/create")
    public ResponseEntity<?> schoolAdminCreate(
            @Valid @RequestBody AdminUserCreateRequest adminUserCreateRequest
    ) throws AlreadyExistException {
        userEndpoint.createAdmin(adminUserCreateRequest);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/rayon/create")
    public ResponseEntity<?> rayonUserCreate(
            @Valid @RequestBody RayonUserCreateRequest rayonUserCreateRequest
    ) throws AlreadyExistException {
        userEndpoint.createRayoner(rayonUserCreateRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/rayon/user/{userId}")
    public List<UserRayonDTO> getUserRayonList(
            @PathVariable("userId") Long userId
    ) {
        return userEndpoint.getUserRayonList(userId);
    }

    @PostMapping("/{user_id}/avatar")
    public ResponseEntity<?> uploadAvatar(
            @RequestPart(name = "file") MultipartFile file,
            @PathVariable(name = "user_id") User user
    ) {
        userEndpoint.uploadAvatar(user, file);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PostMapping("/edit/role")
    public ResponseEntity<?> setRoles(
            @Valid @RequestBody UserRoleRequest userRoleRequest
    ) throws PermissionException {
        userEndpoint.setRoles(userRoleRequest);
        return new ResponseEntity<>(new ApiResponse(true, "Role Updated"), HttpStatus.OK);
    }

    @PostMapping("/switch/role")
    public ResponseEntity<?> switchRole(
            @Valid @RequestBody UserSwitchRole userSwitchRole
    ) throws PermissionException {
        userEndpoint.switchRoles(userSwitchRole);
        return new ResponseEntity<>(new ApiResponse(true, "Person Role Updated"), HttpStatus.OK);
    }

    //@PreAuthorize(value = "hasAnyAuthority('PERM_GRADE_CREATE')")
    @PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping
    public Boolean delete(
            @RequestParam("id") User user
    ) {
        this.userEndpoint.delete(user);
        return true;
    }

    @PostMapping("/search")
    public List<UserProfileDto> searchUser(
            @RequestBody UserSearch userSearch
    ) throws ResourceNotFoundException {
        return userEndpoint.searchUser(userSearch);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/admin")
    public List<SchoolAdminViewDTO> adminList() {
        return userEndpoint.listAdmin();
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/rayon")
    public List<SchoolAdminViewDTO> rayonerList() {
        return userEndpoint.listRayonerUser();
    }
}
