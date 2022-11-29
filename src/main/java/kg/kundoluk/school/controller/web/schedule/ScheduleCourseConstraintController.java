package kg.kundoluk.school.controller.web.schedule;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.projection.ScheduleCourseConstraintDTO;
import kg.kundoluk.school.dto.schedule.ScheduleCourseConstraintCreateRequest;
import kg.kundoluk.school.endpoint.ScheduleCourseConstraintEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/web/v1/schedule/course/constraint")
public class ScheduleCourseConstraintController {
    private final ScheduleCourseConstraintEndpoint scheduleCourseConstraintEndpoint;

    public ScheduleCourseConstraintController(ScheduleCourseConstraintEndpoint scheduleCourseConstraintEndpoint) {
        this.scheduleCourseConstraintEndpoint = scheduleCourseConstraintEndpoint;
    }

    @PostMapping("/create")
    public ResponseEntity<?> bulkCreate(@RequestBody ScheduleCourseConstraintCreateRequest scheduleCourseConstraintCreateRequest) {
        scheduleCourseConstraintEndpoint.create(scheduleCourseConstraintCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, "CREATED"), HttpStatus.OK);
    }

    @DeleteMapping
    public Boolean delete(
            @RequestParam("id") Long id
    ) {
        scheduleCourseConstraintEndpoint.delete(id);
        return true;
    }

    @GetMapping("/shift/{shiftId}")
    public List<ScheduleCourseConstraintDTO> findAllByShift(@PathVariable("shiftId") Long shiftId) {
        return scheduleCourseConstraintEndpoint.findAllByShift(shiftId);
    }

    @GetMapping("/school/{schoolId}")
    public List<ScheduleCourseConstraintDTO> findAllBySchool(@PathVariable("schoolId") Long schoolId) {
        return scheduleCourseConstraintEndpoint.findAllBySchool(schoolId);
    }
}
