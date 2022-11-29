package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.projection.UserSchoolDTO;
import kg.kundoluk.school.dto.user.UserSchoolCreateRequest;
import kg.kundoluk.school.dto.user.UserSchoolRequest;
import kg.kundoluk.school.endpoint.UserSchoolEndpoint;
import kg.kundoluk.school.model.school.School;
import kg.kundoluk.school.model.user.User;
import kg.kundoluk.school.repository.SchoolRepository;
import kg.kundoluk.school.repository.UserRepository;
import kg.kundoluk.school.service.user.UserSchoolService;

import java.util.List;

@Endpoint
public class UserSchoolEndpointImpl implements UserSchoolEndpoint {
    private final UserSchoolService userSchoolService;
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;

    public UserSchoolEndpointImpl(UserSchoolService userSchoolService, SchoolRepository schoolRepository, UserRepository userRepository) {
        this.userSchoolService = userSchoolService;
        this.schoolRepository = schoolRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void create(UserSchoolCreateRequest userSchoolCreateRequest) {
        School school = schoolRepository.getOne(userSchoolCreateRequest.getSchoolId());
        User user = userRepository.getOne(userSchoolCreateRequest.getUserId());
        UserSchoolRequest userSchoolRequest = UserSchoolRequest.builder().school(school).user(user).active(true).build();
        userSchoolService.create(userSchoolRequest);
    }

    @Override
    public void delete(Long id) {
        userSchoolService.delete(id);
    }

    @Override
    public List<UserSchoolDTO> getUserSchool(Long userId) {
        return userSchoolService.getUserSchool(userId);
    }
}
