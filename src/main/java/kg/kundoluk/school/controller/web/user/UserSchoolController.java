package kg.kundoluk.school.controller.web.user;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.projection.UserSchoolDTO;
import kg.kundoluk.school.dto.user.UserSchoolCreateRequest;
import kg.kundoluk.school.endpoint.UserSchoolEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/web/v1/user/school")
public class UserSchoolController {
    private final UserSchoolEndpoint userSchoolEndpoint;

    public UserSchoolController(UserSchoolEndpoint userSchoolEndpoint) {
        this.userSchoolEndpoint = userSchoolEndpoint;
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(
            @RequestBody UserSchoolCreateRequest userSchoolCreateRequest
    )  {
        userSchoolEndpoint.create(userSchoolCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, "User School Created"), HttpStatus.OK);
    }

    @GetMapping("user/{userId}")
    public List<UserSchoolDTO> findAllByUser(@PathVariable("userId") Long userId) {
        return this.userSchoolEndpoint.getUserSchool(userId);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping
    public Boolean delete(
            @RequestParam("id") Long id
    ) {
        this.userSchoolEndpoint.delete(id);
        return true;
    }
}
