package kg.kundoluk.school.service.user;

import kg.kundoluk.school.dto.user.UserApplicationFormRequest;
import kg.kundoluk.school.model.user.UserApplicationForm;
import kg.kundoluk.school.model.enums.FormStatusType;

import java.util.List;

public interface UserApplicationFormService {
    UserApplicationForm create(UserApplicationFormRequest userApplicationFormRequest);
    void process(UserApplicationForm userApplicationForm, FormStatusType formStatusType);
    void delete(Long id);
    Integer checkStatus(String phoneNumber);
    List<UserApplicationForm> listAll();
}
