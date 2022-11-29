package kg.kundoluk.school.controller.web.person;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.person.*;
import kg.kundoluk.school.dto.projection.PersonViewDTO;
import kg.kundoluk.school.dto.student.StudentSearchResult;
import kg.kundoluk.school.dto.user.UserSearch;
import kg.kundoluk.school.endpoint.PersonEndpoint;
import kg.kundoluk.school.exception.PermissionException;
import kg.kundoluk.school.exception.ResourceNotFoundException;
import kg.kundoluk.school.model.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/web/v1/person")
public class PersonController {
    private final PersonEndpoint personEndpoint;

    public PersonController(PersonEndpoint personEndpoint) {
        this.personEndpoint = personEndpoint;
    }

    @GetMapping("/{id}")
    public PersonUserDto get(@PathVariable Long id) {
        return this.personEndpoint.findById(id);
    }

    @GetMapping("/user/{userId}")
    public PersonUserDto getByUserId(@PathVariable Long userId) {
        return this.personEndpoint.findByUserId(userId);
    }

    @GetMapping("/username/{username}")
    public PersonUserDto getByUsername(@PathVariable String username) {
        return this.personEndpoint.findByUsername(username);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PersonCreateRequest personCreateRequest) {
        Person person = this.personEndpoint.create(personCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, person.getId().toString()), HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody PersonUpdateRequest personUpdateRequest, @PathVariable("id") Person person) {
        this.personEndpoint.edit(personUpdateRequest, person);
        return new ResponseEntity<>(new ApiResponse(true, "UPDATED"), HttpStatus.OK);
    }

    @PostMapping("/activate/{id}")
    public ResponseEntity<?> activate(@PathVariable("id") Person person) {
        this.personEndpoint.activate(person);
        return new ResponseEntity<>(new ApiResponse(true, "ACTIVATED"), HttpStatus.OK);
    }

    @DeleteMapping
    public Boolean delete(
            @RequestParam("id") Person person,
            @RequestParam(value = "schoolId", required = false) Long schoolId
    ) throws PermissionException {
        this.personEndpoint.delete(person, schoolId);
        return true;
    }

    @GetMapping("/instructor/school/{schoolId}")
    public List<InstructorDto> instructorListBySchool(@PathVariable("schoolId") Long schoolId, @RequestParam(name = "archived", defaultValue = "false") Boolean archived) {
        return this.personEndpoint.instructorList(schoolId, archived);
    }

    @GetMapping("/school/{schoolId}/role/{roleId}")
    public List<PersonUserDto> listBySchoolAndRole(@PathVariable("schoolId") Long id, @PathVariable("roleId") Long roleId) {
        return this.personEndpoint.findAllBySchoolAndRole(id, roleId);
    }

    @PostMapping("/search")
    public List<PersonSearchResult> searchPerson(
            @RequestBody UserSearch userSearch
    ) throws ResourceNotFoundException {
        return this.personEndpoint.searchPerson(userSearch);
    }

}
