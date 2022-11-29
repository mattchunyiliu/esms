package kg.kundoluk.school.controller.web.person;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.person.PersonCourseBulkRequest;
import kg.kundoluk.school.dto.person.PersonCourseCreateRequest;
import kg.kundoluk.school.dto.projection.SchoolCourseDTO;
import kg.kundoluk.school.endpoint.PersonCourseEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/web/v1/person/course")
public class PersonCourseController {
    private final PersonCourseEndpoint personCourseEndpoint;

    public PersonCourseController(PersonCourseEndpoint personCourseEndpoint) {
        this.personCourseEndpoint = personCourseEndpoint;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PersonCourseCreateRequest personCourseCreateRequest) {
        this.personCourseEndpoint.addCourse(personCourseCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, "CREATED"), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/bulk-operation")
    public ResponseEntity<?> createBulk(@RequestBody PersonCourseBulkRequest personCourseCreateRequest) {
        this.personCourseEndpoint.addBulkCourse(personCourseCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, "CREATED"), HttpStatus.OK);
    }

    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestBody PersonCourseCreateRequest personCourseCreateRequest) {
        this.personCourseEndpoint.removeCourse(personCourseCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, "REMOVE"), HttpStatus.OK);
    }

    @GetMapping("/person/{person_id:[\\d]+}")
    public List<SchoolCourseDTO> list(@PathVariable("person_id") Long id, @RequestParam(value = "schoolId", required = false) Long schoolId) {
        return this.personCourseEndpoint.getAllPersonCourse(id, schoolId);
    }
}
