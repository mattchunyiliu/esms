package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.user.UserApplicationFormCreateRequest;
import kg.kundoluk.school.dto.user.UserApplicationFormResponse;
import kg.kundoluk.school.model.user.UserApplicationForm;
import kg.kundoluk.school.model.enums.FormStatusType;

import java.util.List;

public interface UserApplicationFormEndpoint {
    UserApplicationForm create(UserApplicationFormCreateRequest userApplicationFormCreateRequest);
    void process(UserApplicationForm userApplicationForm, FormStatusType formStatusType);
    void delete(Long id);
    Integer checkStatus(String phoneNumber);
    List<UserApplicationFormResponse> list();
}
