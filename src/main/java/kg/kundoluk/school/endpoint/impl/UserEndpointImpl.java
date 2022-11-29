package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.attachment.AttachmentFileResponseDto;
import kg.kundoluk.school.dto.auth.AuthenticatedUserDto;
import kg.kundoluk.school.dto.projection.SchoolAdminViewDTO;
import kg.kundoluk.school.dto.projection.UserRayonDTO;
import kg.kundoluk.school.dto.user.*;
import kg.kundoluk.school.endpoint.UserEndpoint;
import kg.kundoluk.school.exception.AlreadyExistException;
import kg.kundoluk.school.exception.PermissionException;
import kg.kundoluk.school.exception.ResourceNotFoundException;
import kg.kundoluk.school.mapper.auth.AuthMapper;
import kg.kundoluk.school.mapper.user.UserMapper;
import kg.kundoluk.school.model.references.Role;
import kg.kundoluk.school.model.base.BaseEntity;
import kg.kundoluk.school.model.location.Rayon;
import kg.kundoluk.school.model.user.User;
import kg.kundoluk.school.model.school.School;
import kg.kundoluk.school.repository.RoleRepository;
import kg.kundoluk.school.repository.SchoolRepository;
import kg.kundoluk.school.repository.location.RayonRepository;
import kg.kundoluk.school.service.user.UserAvatarFileService;
import kg.kundoluk.school.service.user.UserSchoolService;
import kg.kundoluk.school.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Endpoint
public class UserEndpointImpl implements UserEndpoint {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final RayonRepository rayonRepository;
    private final SchoolRepository schoolRepository;
    private final UserSchoolService userSchoolService;
    private final UserAvatarFileService userAvatarFileService;
    private final AuthMapper authMapper;
    private final UserMapper userMapper;

    public UserEndpointImpl(UserService userService, RoleRepository roleRepository, RayonRepository rayonRepository, SchoolRepository schoolRepository, UserSchoolService userSchoolService, UserAvatarFileService userAvatarFileService, AuthMapper authMapper, UserMapper userMapper) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.rayonRepository = rayonRepository;
        this.schoolRepository = schoolRepository;
        this.userSchoolService = userSchoolService;
        this.userAvatarFileService = userAvatarFileService;
        this.authMapper = authMapper;
        this.userMapper = userMapper;
    }

    @Override
    public User createAdmin(AdminUserCreateRequest adminUserCreateRequest) throws AlreadyExistException {

        /*if (!StringUtils.isEmpty(adminUserCreateRequest.getPhone()) && userService.existByPhone(adminUserCreateRequest.getPhone()))
            throw new AlreadyExistException(adminUserCreateRequest.getPhone());*/

        Role role = roleRepository.findByCode("ROLE_ADMIN");

        String username = userService.generateUsername(adminUserCreateRequest.getSchoolId(), role.getCode());

        UserRequest userRequest = UserRequest.builder()
                .firstName(adminUserCreateRequest.getFirstName())
                .lastName(adminUserCreateRequest.getLastName())
                .middleName(adminUserCreateRequest.getMiddleName())
                .phone(adminUserCreateRequest.getPhone())
                .roles(Collections.singleton(role))
                .username(username)
                .password(username)
                .build();

        User user = userService.create(userRequest);

        createUserSchool(user, adminUserCreateRequest.getSchoolId());

        return user;
    }

    @Override
    public User createRayoner(RayonUserCreateRequest rayonUserCreateRequest) throws AlreadyExistException {

        if (!StringUtils.isEmpty(rayonUserCreateRequest.getPhone()) && userService.existByPhone(rayonUserCreateRequest.getPhone()))
            throw new AlreadyExistException(rayonUserCreateRequest.getPhone());

        Role role = roleRepository.findByCode("ROLE_RAYON_HEADER");
        Rayon rayon = rayonRepository.findById(rayonUserCreateRequest.getRayonId()).orElse(null);

        String username = userService.generateUsername(rayonUserCreateRequest.getRayonId(), role.getCode());

        UserRequest userRequest = UserRequest.builder()
                .firstName(rayonUserCreateRequest.getFirstName())
                .lastName(rayonUserCreateRequest.getLastName())
                .middleName(rayonUserCreateRequest.getMiddleName())
                .phone(rayonUserCreateRequest.getPhone())
                .roles(Collections.singleton(role))
                .rayons(Collections.singleton(rayon))
                .username(username)
                .password(username)
                .build();

        return userService.create(userRequest);
    }

    @Override
    public List<UserRayonDTO> getUserRayonList(Long userId) {
        return userService.getUserRayons(userId);
    }

    @Override
    public List<SchoolAdminViewDTO> listAdmin() {
        return userService.listSchoolAdmin();
    }

    @Override
    public List<SchoolAdminViewDTO> listRayonerUser() {
        return userService.listRayonAdmin();
    }

    @Override
    public User update(UserProfileUpdateRequest userDto) {
        return userService.update(userDto);
    }

    @Override
    public void uploadAvatar(User user, MultipartFile file) {

        AttachmentFileResponseDto attachmentFileResponseDto = userAvatarFileService.uploadMultipartFile(file);
        UserRequest userRequest = UserRequest.builder()
                .avatar(attachmentFileResponseDto.getUrl())
                .build();
        userService.update(userRequest, user);

    }

    @Override
    public void setRoles(UserRoleRequest userRoleRequest) throws PermissionException {
        User user = userService.findById(userRoleRequest.getUserId());
        if (user.getRoles().stream().anyMatch(r->r.getCode().equals("ROLE_SUPER_ADMIN")))
            throw new PermissionException("CANT_ADD_ROLE_SUPER_ADMIN");
        Set<Role> roles = roleRepository.findAllByIdIn(userRoleRequest.getRoles());
        UserRequest userRequest = UserRequest.builder().roles(roles).build();
        userService.update(userRequest, user);
    }

    private void toUserRoleRequest(Long userId, List<Long> roles) throws PermissionException {
        UserRoleRequest userRoleRequest = new UserRoleRequest(userId, roles);
        setRoles(userRoleRequest);
    }

    @Override
    public void switchRoles(UserSwitchRole userSwitchRole) throws PermissionException {

        // SWITCH OLD ROLES TO INSTRUCTOR
        List<User> users = userService.findAllBySchoolAndRole(userSwitchRole.getSchoolId(), userSwitchRole.getRoleId());

        for (User user: users){
            List<Long> userRoles = userService.findById(user.getId()).getRoles().stream().map(BaseEntity::getId).collect(Collectors.toList());
            userRoles.removeIf(r->r.equals(userSwitchRole.getRoleId()));
            if (userRoles.isEmpty())
                userRoles.add(4L);
            toUserRoleRequest(user.getId(), userRoles);
        }
        User user = userService.findById(userSwitchRole.getUserId());
        List<Long> userRoles = user.getRoles().stream().map(BaseEntity::getId).collect(Collectors.toList());
        userRoles.add(userSwitchRole.getRoleId());
        toUserRoleRequest(userSwitchRole.getUserId(), userRoles);

    }

    @Override
    public UserProfileDto getUserByPhone(String phone) throws ResourceNotFoundException {
        User user = userService.findByPhone(phone);
        if (user == null)
            throw new ResourceNotFoundException(String.format("%s NOT FOUND",phone));
        return userMapper.toUserProfileDto(user);
    }

    @Override
    public UserProfileDto getUserByUsername(String username) throws ResourceNotFoundException {
        User user = userService.getByUsername(username);
        if (user == null)
            throw new ResourceNotFoundException(String.format("%s NOT FOUND",username));
        return userMapper.toUserProfileDto(user);
    }

    @Override
    public List<UserProfileDto> searchUser(UserSearch userSearch) throws ResourceNotFoundException {
        List<User> users = userService.searchUser(userSearch);
        if (users == null)
            throw new ResourceNotFoundException("search not found");
        return users.stream().map(userMapper::toUserProfileDto).collect(Collectors.toList());
    }

    @Override
    public AuthenticatedUserDto getUserById(Long id) {
        return toAuthenticatedUserDto(userService.findById(id));
    }

    @Override
    public void delete(User user) {
        userService.delete(user);
    }

    private AuthenticatedUserDto toAuthenticatedUserDto(User user){
        return authMapper.toAuthenticatedUserDto(user);
    }

    private void createUserSchool(User user, Long schoolId){
        School school = schoolRepository.getOne(schoolId);
        UserSchoolRequest userSchoolRequest = UserSchoolRequest
                .builder()
                .school(school)
                .user(user)
                .active(true)
                .build();
        userSchoolService.create(userSchoolRequest);
    }
}
