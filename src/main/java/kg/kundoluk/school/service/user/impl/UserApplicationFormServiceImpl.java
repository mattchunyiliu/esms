package kg.kundoluk.school.service.user.impl;

import kg.kundoluk.school.dto.user.UserApplicationFormRequest;
import kg.kundoluk.school.model.user.UserApplicationForm;
import kg.kundoluk.school.model.enums.FormStatusType;
import kg.kundoluk.school.repository.UserApplicationFormRepository;
import kg.kundoluk.school.service.user.UserApplicationFormService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserApplicationFormServiceImpl implements UserApplicationFormService {
    private final UserApplicationFormRepository userApplicationFormRepository;

    public UserApplicationFormServiceImpl(UserApplicationFormRepository userApplicationForm) {
        this.userApplicationFormRepository = userApplicationForm;
    }

    @Override
    public UserApplicationForm create(UserApplicationFormRequest userApplicationFormRequest) {
        UserApplicationForm userApplicationForm = new UserApplicationForm()
                .setFirstName(userApplicationFormRequest.getFirstName())
                .setLastName(userApplicationFormRequest.getLastName())
                .setMiddleName(userApplicationFormRequest.getMiddleName())
                .setPhoneNumber(userApplicationFormRequest.getPhoneNumber())
                .setComment(userApplicationFormRequest.getComment())
                .setRole(userApplicationFormRequest.getRole())
                .setSchool(userApplicationFormRequest.getSchool())
                .setSchoolClass(userApplicationFormRequest.getSchoolClass())
                .setStatusType(FormStatusType.NEW);

        return userApplicationFormRepository.save(userApplicationForm);
    }

    @Override
    public void process(UserApplicationForm userApplicationForm, FormStatusType formStatusType) {
        userApplicationFormRepository.save(userApplicationForm.setStatusType(formStatusType));
    }

    @Override
    public void delete(Long id) {
        userApplicationFormRepository.deleteById(id);
    }

    @Override
    public Integer checkStatus(String phoneNumber) {
        return null;
    }

    @Override
    public List<UserApplicationForm> listAll() {
        return userApplicationFormRepository.findAll();
    }
}
