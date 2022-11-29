package kg.kundoluk.school.controller.web.student;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.person.PersonUserDto;
import kg.kundoluk.school.dto.projection.StudentViewDTO;
import kg.kundoluk.school.dto.projection.StudentViewMobileDTO;
import kg.kundoluk.school.dto.student.*;
import kg.kundoluk.school.dto.user.UserProfileDto;
import kg.kundoluk.school.dto.user.UserSearch;
import kg.kundoluk.school.endpoint.StudentEndpoint;
import kg.kundoluk.school.exception.AlreadyExistException;
import kg.kundoluk.school.exception.ConstraintMappingException;
import kg.kundoluk.school.exception.ResourceNotFoundException;
import kg.kundoluk.school.model.student.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/web/v1/student")
public class StudentController {
    private final StudentEndpoint studentEndpoint;

    public StudentController(StudentEndpoint studentEndpoint) {
        this.studentEndpoint = studentEndpoint;
    }

    @GetMapping("/{id}")
    public StudentUserResponse get(@PathVariable("id") Long id) {
        return this.studentEndpoint.findById(id);
    }

    @GetMapping("/user/{user_id}")
    public StudentUserResponse getByUser(@PathVariable("user_id") Long id) {
        return this.studentEndpoint.findByUserId(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody StudentCreateRequest studentCreateRequest) throws AlreadyExistException {
        Student student = this.studentEndpoint.create(studentCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, student.getId().toString()), HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody StudentUpdateRequest studentUpdateRequest, @PathVariable("id") Long id) {
        this.studentEndpoint.edit(studentUpdateRequest, id);
        return new ResponseEntity<>(new ApiResponse(true, "UPDATED"), HttpStatus.OK);
    }

    @PostMapping("/class-edit")
    public ResponseEntity<?> classEdit(@RequestBody StudentClassUpdateRequest studentUpdateRequest) {
        this.studentEndpoint.editClass(studentUpdateRequest);
        return new ResponseEntity<>(new ApiResponse(true, "UPDATED"), HttpStatus.OK);
    }

    @DeleteMapping
    public Boolean delete(
            @RequestParam("id") Student student
    ) throws ConstraintMappingException {
        return this.studentEndpoint.delete(student);
    }

    @GetMapping("/school/{schoolId}")
    public List<StudentViewDTO> listBySchool(@PathVariable("schoolId") Long id, @RequestParam(value = "archived", required = false, defaultValue = "false") Boolean archived) {
        return this.studentEndpoint.studentListBySchool(id, archived);
    }

    @GetMapping("/class/{classId}")
    public List<StudentViewMobileDTO> listByClass(@PathVariable("classId") Long id) {
        return this.studentEndpoint.studentListByClass(id);
    }

    @PostMapping("/search")
    public List<StudentSearchResult> searchStudent(
            @RequestBody UserSearch userSearch
    ) throws ResourceNotFoundException {
        return studentEndpoint.searchStudent(userSearch);
    }

}
