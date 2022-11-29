package kg.kundoluk.school.controller.mobile.calendar;

import kg.kundoluk.school.components.hateoas.assembler.CalendarTopicResourceAssembler;
import kg.kundoluk.school.components.hateoas.resource.CalendarTopicResource;
import kg.kundoluk.school.constants.Constants;
import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.calendar.CalendarTopicCreateRequest;
import kg.kundoluk.school.dto.calendar.CalendarTopicResponse;
import kg.kundoluk.school.dto.calendar.CalendarTopicUpdateRequest;
import kg.kundoluk.school.endpoint.CalendarTopicEndpoint;
import kg.kundoluk.school.exception.ConstraintMappingException;
import kg.kundoluk.school.model.instructor.CalendarTopic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mobile/v1/calendar/topic")
public class CalendarTopicController {
    private final CalendarTopicEndpoint calendarTopicEndpoint;
    private final CalendarTopicResourceAssembler assembler;

    public CalendarTopicController(CalendarTopicEndpoint calendarTopicEndpoint, CalendarTopicResourceAssembler assembler) {
        this.calendarTopicEndpoint = calendarTopicEndpoint;
        this.assembler = assembler;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CalendarTopicCreateRequest calendarTopicCreateRequest) {
        calendarTopicEndpoint.create(calendarTopicCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, Constants.CREATED), HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody CalendarTopicUpdateRequest calendarTopicUpdateRequest, @PathVariable("id") CalendarTopic calendarTopic) {
        calendarTopicEndpoint.edit(calendarTopicUpdateRequest, calendarTopic);
        return new ResponseEntity<>(new ApiResponse(true, "UPDATED"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public CalendarTopicResource get(@PathVariable("id") Long id) {
        return assembler.toModel(calendarTopicEndpoint.findById(id));
    }

    @DeleteMapping
    public Boolean delete(
            @RequestParam("id") Long id
    ) throws ConstraintMappingException {
        calendarTopicEndpoint.delete(id);
        return true;
    }

    @GetMapping("plan/{planId}")
    public List<CalendarTopicResponse> findAllByCalendarPlan(
            @PathVariable("planId") Long planId
    ) {
        return calendarTopicEndpoint.findAllByCalendarPlan(planId);
    }

    @GetMapping("instructor/{instructorId}/class/{classId}/course/{courseId}/chronicle/{chronicleId}")
    public List<CalendarTopicResponse> findAllByInstructorChronicle(
            @PathVariable("instructorId") Long instructorId,
            @PathVariable("classId") Long classId,
             @PathVariable("courseId") Long courseId,
            @PathVariable("chronicleId") Long chronicleId
    ) {
        return calendarTopicEndpoint.findAllByInstructorClassCourse(instructorId, classId, courseId, chronicleId);
    }
}
