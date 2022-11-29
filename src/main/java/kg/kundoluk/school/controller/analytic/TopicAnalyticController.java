package kg.kundoluk.school.controller.analytic;

import kg.kundoluk.school.dto.projection.AnalyticSchoolCount;
import kg.kundoluk.school.dto.projection.CalendarTopicClass;
import kg.kundoluk.school.dto.projection.GradeCountAnalytic;
import kg.kundoluk.school.endpoint.TopicAnalyticEndpoint;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/analytic/v1/topic/analytic")
public class TopicAnalyticController {
    private final TopicAnalyticEndpoint topicAnalyticEndpoint;

    public TopicAnalyticController(TopicAnalyticEndpoint topicAnalyticEndpoint) {
        this.topicAnalyticEndpoint = topicAnalyticEndpoint;
    }

    @GetMapping("/count/school/{schoolId}")
    public List<GradeCountAnalytic> getBySchoolGradeCount(
            @PathVariable("schoolId") Long schoolId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        return topicAnalyticEndpoint.getInstructorTopicCount(schoolId, startDate, endDate);
    }

    @GetMapping("/count/rayon/{rayonId}")
    public List<AnalyticSchoolCount> getByCountByRayon(
            @PathVariable("rayonId") Long rayonId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        return topicAnalyticEndpoint.getTopicCountBySchool(rayonId, startDate, endDate);
    }

    @GetMapping("/instructor/{instructorId}/class/{classId}")
    public List<CalendarTopicClass> getInstructorClassList(
            @PathVariable("instructorId") Long instructorId,
            @PathVariable("classId") Long classId
    ) {
        return topicAnalyticEndpoint.getInstructorClassList(instructorId, classId);
    }
}
