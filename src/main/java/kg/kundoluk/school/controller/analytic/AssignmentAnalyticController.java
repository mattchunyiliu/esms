package kg.kundoluk.school.controller.analytic;

import kg.kundoluk.school.dto.projection.AnalyticSchoolCount;
import kg.kundoluk.school.dto.projection.AssignmentInstructorClass;
import kg.kundoluk.school.dto.projection.GradeCountAnalytic;
import kg.kundoluk.school.endpoint.AssignmentAnalyticEndpoint;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/analytic/v1/assignment/analytic")
public class AssignmentAnalyticController {
    private final AssignmentAnalyticEndpoint assignmentAnalyticEndpoint;

    public AssignmentAnalyticController(AssignmentAnalyticEndpoint assignmentAnalyticEndpoint) {
        this.assignmentAnalyticEndpoint = assignmentAnalyticEndpoint;
    }

    @GetMapping("/count/school/{schoolId}")
    public List<GradeCountAnalytic> getByAssignmentCountByInstructor(
            @PathVariable("schoolId") Long schoolId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        return assignmentAnalyticEndpoint.getInstructorAssignmentCount(schoolId, startDate, endDate);
    }

    @GetMapping("/count/rayon/{rayonId}")
    public List<AnalyticSchoolCount> getByAssignmentCountBySchool(
            @PathVariable("rayonId") Long rayonId,
            @RequestParam(value = "townId", required = false) Long townId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        return assignmentAnalyticEndpoint.getSchoolAssignmentCount(rayonId, townId, startDate, endDate);
    }

    @GetMapping("/instructor/{instructorId}/class/{classId}")
    public List<AssignmentInstructorClass> getInstructorClassList(
            @PathVariable("instructorId") Long instructorId,
            @PathVariable("classId") Long classId
    ) {
        return assignmentAnalyticEndpoint.getInstructorClassList(instructorId, classId);
    }
}
