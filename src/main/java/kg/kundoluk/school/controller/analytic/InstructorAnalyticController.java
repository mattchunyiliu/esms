package kg.kundoluk.school.controller.analytic;

import kg.kundoluk.school.dto.projection.AnalyticGender;
import kg.kundoluk.school.endpoint.StatisticEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/analytic/v1/instructor/analytic")
public class InstructorAnalyticController {
    private final StatisticEndpoint statisticEndpoint;

    public InstructorAnalyticController(StatisticEndpoint statisticEndpoint) {
        this.statisticEndpoint = statisticEndpoint;
    }

    @GetMapping("/gender")
    public List<AnalyticGender> getGenderAnalytic(
            @RequestParam(value = "schoolId",required = false) Long schoolId,
            @RequestParam(value = "rayonId",required = false) Long rayonId,
            @RequestParam(value = "townId",required = false) Long townId
    ) {
        return statisticEndpoint.getAllInstructorGenderAnalytic(schoolId, rayonId, townId);
    }
}
