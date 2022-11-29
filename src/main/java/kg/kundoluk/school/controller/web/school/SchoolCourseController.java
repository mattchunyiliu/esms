package kg.kundoluk.school.controller.web.school;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.projection.SchoolCourseDTO;
import kg.kundoluk.school.dto.school.SchoolCourseBulkRequest;
import kg.kundoluk.school.dto.school.SchoolCourseCreateRequest;
import kg.kundoluk.school.service.school.SchoolCourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/web/v1/school/course")
public class SchoolCourseController {
    private final SchoolCourseService service;

    public SchoolCourseController(SchoolCourseService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SchoolCourseCreateRequest schoolCourseCreateRequest) {
        service.create(schoolCourseCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, "CREATED"), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/bulk-operation")
    public ResponseEntity<?> createBulk(@RequestBody SchoolCourseBulkRequest schoolCourseCreateRequest) {
        service.createBulk(schoolCourseCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, "CREATED"), HttpStatus.OK);
    }

    @DeleteMapping
    public Boolean delete(
            @RequestParam("id") Long id
    ) {
        this.service.delete(id);
        return true;
    }

    @GetMapping("school/{schoolId}")
    public List<SchoolCourseDTO> findAllBySchool(@PathVariable("schoolId") Long schoolId) {
        return this.service.findAllBySchool(schoolId);
    }
}
