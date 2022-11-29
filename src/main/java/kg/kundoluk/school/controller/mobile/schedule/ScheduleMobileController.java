package kg.kundoluk.school.controller.mobile.schedule;

import kg.kundoluk.school.dto.schedule.ScheduleResponse;
import kg.kundoluk.school.dto.schoolClass.SchoolClassResource;
import kg.kundoluk.school.endpoint.ScheduleEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mobile/v1/schedule")
public class ScheduleMobileController {
    private final ScheduleEndpoint scheduleEndpoint;

    public ScheduleMobileController(ScheduleEndpoint scheduleEndpoint) {
        this.scheduleEndpoint = scheduleEndpoint;
    }

    @GetMapping("/instructor/{instructorId}/date/{date}")
    public List<ScheduleResponse> findAllByInstructorDate(
            @PathVariable("instructorId") Long instructorId,
            @PathVariable("date") String date
    ) {
        return this.scheduleEndpoint.findAllByInstructorDate(instructorId, date);
    }

    @GetMapping("/class/{classId}/date/{date}")
    public List<ScheduleResponse> findAllByClassDate(@PathVariable("classId") Long classId, @PathVariable("date") String date) {
        return scheduleEndpoint.findAllByClassDate(classId, date);
    }
}
