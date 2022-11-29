package kg.kundoluk.school.controller.web.schedule;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.schedule.ScheduleClassLoadCreateRequest;
import kg.kundoluk.school.dto.schedule.ScheduleClassLoadResponse;
import kg.kundoluk.school.dto.schedule.ScheduleClassLoadUpdateRequest;
import kg.kundoluk.school.endpoint.ScheduleClassLoadEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/web/v1/schedule/class/load")
public class ScheduleClassLoadController {
    private final ScheduleClassLoadEndpoint scheduleClassLoadEndpoint;

    public ScheduleClassLoadController(ScheduleClassLoadEndpoint scheduleClassLoadEndpoint) {
        this.scheduleClassLoadEndpoint = scheduleClassLoadEndpoint;
    }

    @PostMapping("/create")
    public ResponseEntity<?> bulkCreate(@RequestBody List<ScheduleClassLoadCreateRequest> scheduleClassLoadCreateRequests) {
        scheduleClassLoadEndpoint.createBulk(scheduleClassLoadCreateRequests);
        return new ResponseEntity<>(new ApiResponse(true, "CREATED"), HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> bulkEdit(@RequestBody List<ScheduleClassLoadUpdateRequest> scheduleClassLoadCreateRequests) {
        scheduleClassLoadEndpoint.editBulk(scheduleClassLoadCreateRequests);
        return new ResponseEntity<>(new ApiResponse(true, "UPDATED"), HttpStatus.OK);
    }

    @DeleteMapping
    public Boolean delete(
            @RequestParam("id") Long id
    ) {
        scheduleClassLoadEndpoint.delete(id);
        return true;
    }

    @GetMapping("/school/{schoolId}")
    public List<ScheduleClassLoadResponse> findAllBySchool(@PathVariable("schoolId") Long schoolId) {
        return scheduleClassLoadEndpoint.findAllBySchool(schoolId);
    }
}
