package kg.kundoluk.school.controller.mobile.calendar;


import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.calendar.*;
import kg.kundoluk.school.endpoint.CalendarPlanEndpoint;
import kg.kundoluk.school.exception.ConstraintMappingException;
import kg.kundoluk.school.model.instructor.CalendarPlan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mobile/v1/calendar/plan")
public class CalendarPlanController {
    private final CalendarPlanEndpoint calendarPlanEndpoint;

    public CalendarPlanController(CalendarPlanEndpoint calendarPlanEndpoint) {
        this.calendarPlanEndpoint = calendarPlanEndpoint;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CalendarPlanCreateRequest calendarPlanCreateRequest) {
        CalendarPlan calendarPlan = calendarPlanEndpoint.create(calendarPlanCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, calendarPlan.getId().toString()), HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody String title, @PathVariable("id") CalendarPlan calendarPlan) {
        calendarPlanEndpoint.edit(title, calendarPlan);
        return new ResponseEntity<>(new ApiResponse(true, "UPDATED"), HttpStatus.OK);
    }

    @PostMapping("/edit-all/{id}")
    public ResponseEntity<?> editAll(@RequestBody CalendarPlanUpdateRequest calendarPlanUpdateRequest, @PathVariable("id") CalendarPlan calendarPlan) {
        calendarPlanEndpoint.edit(calendarPlanUpdateRequest, calendarPlan);
        return new ResponseEntity<>(new ApiResponse(true, "UPDATED"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public CalendarPlanResponse get(@PathVariable("id") Long id) {
        return calendarPlanEndpoint.getById(id);
    }

    @PostMapping("/clone")
    public ResponseEntity<?> clone(@RequestBody CalendarPlanCloneCreateRequest calendarPlanCloneCreateRequest) {
        Integer totalTopic = calendarPlanEndpoint.clone(calendarPlanCloneCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, totalTopic.toString()), HttpStatus.OK);
    }


    @DeleteMapping
    public Boolean delete(
            @RequestParam("id") Long id
    ) throws ConstraintMappingException {
        calendarPlanEndpoint.delete(id);
        return true;
    }

    @GetMapping("instructor/{instructorId}/chronicle/{chronicleId}")
    public List<CalendarPlanResponse> findAllByInstructorAndChronicle(
            @PathVariable("instructorId") Long instructorId,
            @PathVariable("chronicleId") Long chronicleIdId
    ) {
        return calendarPlanEndpoint.findAllByInstructorChronicle(instructorId, chronicleIdId);
    }
}
