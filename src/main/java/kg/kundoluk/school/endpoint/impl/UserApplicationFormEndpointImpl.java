package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.user.UserApplicationFormCreateRequest;
import kg.kundoluk.school.dto.user.UserApplicationFormRequest;
import kg.kundoluk.school.dto.user.UserApplicationFormResponse;
import kg.kundoluk.school.endpoint.UserApplicationFormEndpoint;
import kg.kundoluk.school.model.user.UserApplicationForm;
import kg.kundoluk.school.model.enums.FormStatusType;
import kg.kundoluk.school.repository.RoleRepository;
import kg.kundoluk.school.repository.SchoolClassRepository;
import kg.kundoluk.school.repository.SchoolRepository;
import kg.kundoluk.school.service.user.UserApplicationFormService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Endpoint
public class UserApplicationFormEndpointImpl implements UserApplicationFormEndpoint {
    private final UserApplicationFormService userApplicationFormService;
    private final SchoolRepository schoolRepository;
    private final RoleRepository roleRepository;
    private final SchoolClassRepository schoolClassRepository;

    public UserApplicationFormEndpointImpl(UserApplicationFormService userApplicationFormService, SchoolRepository schoolRepository, RoleRepository roleRepository, SchoolClassRepository schoolClassRepository) {
        this.userApplicationFormService = userApplicationFormService;
        this.schoolRepository = schoolRepository;
        this.roleRepository = roleRepository;
        this.schoolClassRepository = schoolClassRepository;
    }

    @Override
    public UserApplicationForm create(UserApplicationFormCreateRequest userApplicationFormCreateRequest) {
        UserApplicationFormRequest userApplicationFormRequest = UserApplicationFormRequest.builder()
                .firstName(userApplicationFormCreateRequest.getFirstName())
                .lastName(userApplicationFormCreateRequest.getLastName())
                .middleName(userApplicationFormCreateRequest.getMiddleName())
                .comment(userApplicationFormCreateRequest.getComment())
                .phoneNumber(userApplicationFormCreateRequest.getPhoneNumber())
                .role(roleRepository.getOne(userApplicationFormCreateRequest.getRoleId()))
                .school(schoolRepository.getOne(userApplicationFormCreateRequest.getSchoolId()))
                .schoolClass(userApplicationFormCreateRequest.getClassId()!=null?schoolClassRepository.getOne(userApplicationFormCreateRequest.getClassId()):null)
                .build();

        return userApplicationFormService.create(userApplicationFormRequest);
    }

    @Override
    public void process(UserApplicationForm userApplicationForm, FormStatusType formStatusType) {
        userApplicationFormService.process(userApplicationForm, formStatusType);
    }

    @Override
    public void delete(Long id) {
        userApplicationFormService.delete(id);
    }

    @Override
    public Integer checkStatus(String phoneNumber) {
        return userApplicationFormService.checkStatus(phoneNumber);
    }

    @Override
    public List<UserApplicationFormResponse> list() {
        return userApplicationFormService.listAll().stream().map(this::toUserApplicationFormResponse).collect(Collectors.toList());
    }

    private UserApplicationFormResponse toUserApplicationFormResponse(UserApplicationForm userApplicationForm){
        UserApplicationFormResponse userApplicationFormResponse = new UserApplicationFormResponse();
        userApplicationFormResponse.setId(userApplicationForm.getId());
        userApplicationFormResponse.setFirstName(userApplicationForm.getFirstName());
        userApplicationFormResponse.setLastName(userApplicationForm.getLastName());
        userApplicationFormResponse.setMiddleName(userApplicationForm.getMiddleName());
        userApplicationFormResponse.setComment(userApplicationForm.getComment());
        userApplicationFormResponse.setPhoneNumber(userApplicationForm.getPhoneNumber());
        userApplicationFormResponse.setSchoolTitle(userApplicationForm.getSchool().getName());
        userApplicationFormResponse.setRoleTitle(userApplicationForm.getRole().getTitle());
        userApplicationFormResponse.setStatusType(userApplicationForm.getStatusType());
        if (Objects.nonNull(userApplicationForm.getSchoolClass()))
            userApplicationFormResponse.setSchoolTitle(userApplicationForm.getSchoolClass().getSelectorTitle());
        return userApplicationFormResponse;
    }
}
