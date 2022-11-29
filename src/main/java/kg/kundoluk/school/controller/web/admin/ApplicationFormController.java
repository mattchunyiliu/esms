package kg.kundoluk.school.controller.web.admin;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.user.UserApplicationFormCreateRequest;
import kg.kundoluk.school.dto.user.UserApplicationFormResponse;
import kg.kundoluk.school.endpoint.UserApplicationFormEndpoint;
import kg.kundoluk.school.model.user.UserApplicationForm;
import kg.kundoluk.school.model.enums.FormStatusType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/web/v1/application/form")
public class ApplicationFormController {
    private final UserApplicationFormEndpoint userApplicationFormEndpoint;

    public ApplicationFormController(UserApplicationFormEndpoint userApplicationFormEndpoint) {
        this.userApplicationFormEndpoint = userApplicationFormEndpoint;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(
            @RequestBody UserApplicationFormCreateRequest requestDto
    ) {
        userApplicationFormEndpoint.create(requestDto);
        return new ResponseEntity<>(new ApiResponse(true, "CREATED"), HttpStatus.OK);
    }

    @GetMapping("/list")
    public List<UserApplicationFormResponse> list() {
        return userApplicationFormEndpoint.list();
    }

    @PostMapping("/process/{id}")
    public ResponseEntity<?> process(
            @PathVariable("id") UserApplicationForm userApplicationForm,
            @RequestParam("status") FormStatusType statusType
    ) {
        userApplicationFormEndpoint.process(userApplicationForm, statusType);
        return new ResponseEntity<>(new ApiResponse(true, "PROCESSED"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(
            @PathVariable(name = "id") Long id
    ) {
        userApplicationFormEndpoint.delete(id);
        return true;
    }

    @PostMapping("/check")
    public ResponseEntity<?> checkStatus(
            @RequestParam(name = "phoneNumber") String phoneNumber
    ) {
        Integer status = userApplicationFormEndpoint.checkStatus(phoneNumber);
        boolean flag = false;
        String result = "NOT_FOUND";
        if (status != null) {
            result = String.valueOf(status);
            flag = true;
        }
        return new ResponseEntity<>(new ApiResponse(flag, result), HttpStatus.OK);
    }
}
