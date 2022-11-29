package kg.kundoluk.school.controller.web.student;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.projection.FirebaseTokenDTO;
import kg.kundoluk.school.dto.student.parent.StudentParentBulkDto;
import kg.kundoluk.school.dto.student.parent.StudentParentCreateRequest;
import kg.kundoluk.school.dto.student.parent.StudentParentUserResponse;
import kg.kundoluk.school.dto.student.StudentUserResponse;
import kg.kundoluk.school.endpoint.StudentParentEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/web/v1/parent")
public class StudentParentController {

    private final StudentParentEndpoint studentParentEndpoint;

    public StudentParentController(StudentParentEndpoint studentParentEndpoint) {
        this.studentParentEndpoint = studentParentEndpoint;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(
            @Valid @RequestBody StudentParentCreateRequest studentParentCreateRequest
    ) {
        this.studentParentEndpoint.create(studentParentCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, "New Parent Student Mapped"), HttpStatus.OK);
    }

    @GetMapping("/student/{student_id}")
    public List<StudentParentUserResponse> getAllByStudent(
            @PathVariable(name = "student_id") Long id
    ) {
        return this.studentParentEndpoint.getByStudent(id);
    }

    @GetMapping("/class/{class_id}")
    public List<StudentParentUserResponse> getAllByClass(
            @PathVariable(name = "class_id") Long id
    ) {
        return this.studentParentEndpoint.getByClass(id);
    }

    @GetMapping("/person/{person_id}")
    public List<StudentUserResponse> getAllByPerson(
            @PathVariable(name = "person_id") Long id
    ) {
        return this.studentParentEndpoint.getByPerson(id);
    }

    @GetMapping("/person/user/{username}")
    public List<StudentParentUserResponse> getAllByPersonUsername(
            @PathVariable String username
    ) {
        return this.studentParentEndpoint.getByPersonUsername(username);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/substitute-student")
    public ResponseEntity<?> substituteStudent(
            @Valid @RequestBody StudentParentBulkDto studentParentBulkDto,
            Authentication authentication
    ) {
        this.studentParentEndpoint.substituteStudent(studentParentBulkDto, authentication);
        return new ResponseEntity<>(new ApiResponse(true,  "Changed"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") Long id
    ) {
        this.studentParentEndpoint.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tokens/student/{student_id}")
    public List<FirebaseTokenDTO> getStudentParentTokens(
            @PathVariable(name = "student_id") Long id
    ) {
        return this.studentParentEndpoint.findParentTokensByStudent(id);
    }
}
