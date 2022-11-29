package kg.kundoluk.school.controller.web.schedule;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.schedule.ScheduleCreateRequest;
import kg.kundoluk.school.dto.schedule.ScheduleMonthResponse;
import kg.kundoluk.school.dto.schedule.ScheduleResponse;
import kg.kundoluk.school.dto.schedule.ScheduleUpdateRequest;
import kg.kundoluk.school.endpoint.ScheduleEndpoint;
import kg.kundoluk.school.exception.AlreadyExistException;
import kg.kundoluk.school.model.schedule.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/web/v1/schedule")
public class ScheduleController {

    private final ScheduleEndpoint scheduleEndpoint;

    public ScheduleController(ScheduleEndpoint scheduleEndpoint) {
        this.scheduleEndpoint = scheduleEndpoint;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ScheduleCreateRequest scheduleCreateRequest) throws AlreadyExistException {
        scheduleEndpoint.create(scheduleCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, "CREATED"), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create-massive")
    public ResponseEntity<?> createMassive(@RequestBody List<ScheduleCreateRequest> scheduleCreateRequests) throws AlreadyExistException {
        scheduleEndpoint.createMassive(scheduleCreateRequests);
        return new ResponseEntity<>(new ApiResponse(true, "CREATED"), HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody ScheduleUpdateRequest scheduleUpdateRequest, @PathVariable("id") Schedule schedule) {
        scheduleEndpoint.edit(scheduleUpdateRequest, schedule);
        return new ResponseEntity<>(new ApiResponse(true, "UPDATED"), HttpStatus.OK);
    }

    @DeleteMapping
    public Boolean delete(
            @RequestParam("id") Long id
    ) {
        scheduleEndpoint.delete(id);
        return true;
    }

    @DeleteMapping("/school/{schoolId}/shift/{shiftId}")
    public Boolean deleteBySchool(
            @PathVariable("schoolId") Long schoolId,
            @PathVariable("shiftId") Long shiftId
    ) {
        scheduleEndpoint.deleteBySchool(schoolId, shiftId);
        return true;
    }

    @GetMapping("/{id}")
    public ScheduleResponse findAllById(@PathVariable("id") Long id) {
        return scheduleEndpoint.findById(id);
    }

    @GetMapping("/school/{schoolId}")
    public List<ScheduleResponse> findAllBySchool(@PathVariable("schoolId") Long schoolId) {
        return scheduleEndpoint.findAllBySchool(schoolId);
    }

    @GetMapping("/class/{classId}")
    public List<ScheduleResponse> findAllByClass(@PathVariable("classId") Long classId) {
        return scheduleEndpoint.findAllByClass(classId);
    }

    @GetMapping("/instructor/{instructorId}")
    public List<ScheduleResponse> findAllByInstructor(
            @PathVariable("instructorId") Long instructorId
    ) {
        return this.scheduleEndpoint.findAllByInstructor(instructorId);
    }

    @GetMapping("/month/instructor/{instructorId}/class/{classId}/course/{courseId}")
    public List<ScheduleMonthResponse> showMonth(
            @PathVariable("instructorId") Long instructorId,
            @PathVariable("classId") Long classId,
            @PathVariable("courseId") Long courseId,
            @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate") String endDate
    ) {
        return scheduleEndpoint.findAllInstructorScheduleMonth(instructorId, classId, courseId, startDate, endDate);
    }

    @GetMapping("/month/class/{classId}")
    public List<ScheduleMonthResponse> showMonthClass(
            @PathVariable("classId") Long classId,
            @RequestParam(name = "courseId", required = false) Long courseId,
            @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate") String endDate
    ) {
        return scheduleEndpoint.findAllClassScheduleMonth(classId, courseId, startDate, endDate);
    }
}
