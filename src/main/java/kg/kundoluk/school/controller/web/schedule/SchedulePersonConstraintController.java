package kg.kundoluk.school.controller.web.schedule;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.projection.SchedulePersonConstraintDTO;
import kg.kundoluk.school.dto.schedule.SchedulePersonConstraintCreateRequest;
import kg.kundoluk.school.endpoint.SchedulePersonConstraintEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/web/v1/schedule/person/constraint")
public class SchedulePersonConstraintController {
    private final SchedulePersonConstraintEndpoint schedulePersonConstraintEndpoint;

    public SchedulePersonConstraintController(SchedulePersonConstraintEndpoint schedulePersonConstraintEndpoint) {
        this.schedulePersonConstraintEndpoint = schedulePersonConstraintEndpoint;
    }

    @PostMapping("/create")
    public ResponseEntity<?> bulkCreate(@RequestBody SchedulePersonConstraintCreateRequest schedulePersonConstraintCreateRequest) {
        schedulePersonConstraintEndpoint.create(schedulePersonConstraintCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, "CREATED"), HttpStatus.OK);
    }

    @DeleteMapping
    public Boolean delete(
            @RequestParam("id") Long id
    ) {
        schedulePersonConstraintEndpoint.delete(id);
        return true;
    }

    @GetMapping("/shift/{shiftId}")
    public List<SchedulePersonConstraintDTO> findAllByShift(@PathVariable("shiftId") Long shiftId) {
        return schedulePersonConstraintEndpoint.findAllByShift(shiftId);
    }

    @GetMapping("/school/{schoolId}")
    public List<SchedulePersonConstraintDTO> findAllBySchool(@PathVariable("schoolId") Long schoolId) {
        return schedulePersonConstraintEndpoint.findAllBySchool(schoolId);
    }
}
