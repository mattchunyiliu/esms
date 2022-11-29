package kg.kundoluk.school.controller.analytic;

import kg.kundoluk.school.dto.projection.AnalyticClassGender;
import kg.kundoluk.school.dto.projection.AnalyticGender;
import kg.kundoluk.school.endpoint.StatisticEndpoint;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/analytic/v1/student/analytic")
public class StudentAnalyticController {
    private final StatisticEndpoint statisticEndpoint;

    public StudentAnalyticController(StatisticEndpoint statisticEndpoint) {
        this.statisticEndpoint = statisticEndpoint;
    }

    @GetMapping("/gender")
    public List<AnalyticGender> getGenderAnalytic(
            @RequestParam(value = "schoolId",required = false) Long schoolId,
            @RequestParam(value = "rayonId",required = false) Long rayonId,
            @RequestParam(value = "townId",required = false) Long townId
    ) {
        return statisticEndpoint.getAllStudentGenderAnalytic(schoolId, rayonId, townId);
    }

    @GetMapping("/school/{schoolId}")
    public List<AnalyticClassGender> getSchoolClassGenderAnalytic(
            @PathVariable Long schoolId,
            @RequestParam(value = "classId",required = false) Long classId
    ) {
        return statisticEndpoint.getSchoolClassGenderAnalytic(schoolId, classId);
    }
}
