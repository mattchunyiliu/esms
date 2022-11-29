package kg.kundoluk.school.controller.web.schedule;

import kg.kundoluk.school.constants.Constants;
import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.schedule.*;
import kg.kundoluk.school.endpoint.ScheduleGroupEndpoint;
import kg.kundoluk.school.model.schedule.ScheduleGroup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/web/v1/schedule/group")
public class ScheduleGroupController {
    private final ScheduleGroupEndpoint scheduleGroupEndpoint;

    public ScheduleGroupController(ScheduleGroupEndpoint scheduleGroupEndpoint) {
        this.scheduleGroupEndpoint = scheduleGroupEndpoint;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ScheduleGroupCreateRequest scheduleGroupCreateRequest) {
        scheduleGroupEndpoint.create(scheduleGroupCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, "CREATED"), HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody ScheduleGroupUpdateRequest scheduleGroupUpdateRequest, @PathVariable("id")ScheduleGroup scheduleGroup) {
        scheduleGroupEndpoint.edit(scheduleGroupUpdateRequest, scheduleGroup);
        return new ResponseEntity<>(new ApiResponse(true, Constants.UPDATED), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ScheduleGroupSingleResponse get(@PathVariable("id") Long id) {
        return scheduleGroupEndpoint.findById(id);
    }

    @DeleteMapping
    public Boolean delete(
            @RequestParam("id") Long id,
            @RequestParam("schoolId") Long schoolId
    ) {
        this.scheduleGroupEndpoint.delete(id, schoolId);
        return true;
    }

    @GetMapping("school/{schoolId}")
    public List<ScheduleGroupResponse> findAllBySchool(@PathVariable("schoolId") Long schoolId) {
        return scheduleGroupEndpoint.findAllBySchool(schoolId);
    }

    @GetMapping("class/{classId}")
    public List<ScheduleGroupResponse> findAllByClass(@PathVariable("classId") Long classId) {
        return scheduleGroupEndpoint.findAllByClass(classId);
    }

    @GetMapping("student/{studentId}")
    public List<ScheduleGroupResponse> findAllByStudent(@PathVariable("studentId") Long studentId) {
        return scheduleGroupEndpoint.findAllByStudent(studentId);
    }
}
