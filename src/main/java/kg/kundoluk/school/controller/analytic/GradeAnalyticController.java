package kg.kundoluk.school.controller.analytic;

import kg.kundoluk.school.dto.projection.*;
import kg.kundoluk.school.endpoint.GradeAnalyticEndpoint;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/analytic/v1/grade/analytic")
public class GradeAnalyticController {
    private final GradeAnalyticEndpoint gradeAnalyticEndpoint;

    public GradeAnalyticController(GradeAnalyticEndpoint gradeAnalyticEndpoint) {
        this.gradeAnalyticEndpoint = gradeAnalyticEndpoint;
    }

    @GetMapping("/student/{studentId}")
    public List<GradeStudentAnalytic> getByStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        return gradeAnalyticEndpoint.findAllByStudent(studentId, startDate, endDate);
    }

    @GetMapping("/class/{classId}")
    public List<GradeStudentAnalytic> getByClass(
            @PathVariable("classId") Long classId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam(value = "courseId", required = false) Long courseId,
            @RequestParam(value = "isCourse", required = false, defaultValue = "false") Boolean isCourse
    ) {
        return gradeAnalyticEndpoint.findAllByClass(classId, courseId, startDate, endDate, isCourse);
    }

    @GetMapping("/school/{schoolId}")
    public List<GradeGroupMonthAnalytic> getBySchool(
            @PathVariable("schoolId") Long schoolId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        return gradeAnalyticEndpoint.getGradesGroupByClassMonth(schoolId, startDate, endDate);
    }

    @GetMapping("/grade-count/rayon/{rayonId}")
    public List<GradeSchoolGroupAnalytic> getByRayonGradeCount(
            @PathVariable("rayonId") Long rayonId,
            @RequestParam(value = "townId", required = false) Long townId,
            @RequestParam(value = "schoolId", required = false) Long schoolId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        return gradeAnalyticEndpoint.getSchoolGradeCount(rayonId, townId, schoolId, startDate, endDate);
    }

    @GetMapping("/grade-count/school/{schoolId}")
    public List<GradeCountAnalytic> getBySchoolGradeCount(
            @PathVariable("schoolId") Long schoolId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        return gradeAnalyticEndpoint.getInstructorGradeCount(schoolId, startDate, endDate);
    }

    @GetMapping("/grade-count/instructor/{instructorId}")
    public List<GradeInstructorClassGroupAnalytic> getByInstructorClassGradeCount(
            @PathVariable("instructorId") Long instructorId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        return gradeAnalyticEndpoint.getInstructorGroupClassGradeCount(instructorId, startDate, endDate);
    }

    @GetMapping("/instructor/{instructorId}/class/{classId}")
    public List<GradeStudentDate> getByInstructorClassGradeList(
            @PathVariable("instructorId") Long instructorId,
            @PathVariable("classId") Long classId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        return gradeAnalyticEndpoint.getInstructorClassGradeList(instructorId, classId, startDate, endDate);
    }

    @GetMapping("/instructor/activity/school/{schoolId}")
    public List<GradeInstructorMonth> getByInstructorActivityByMonth(
            @PathVariable("schoolId") Long schoolId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        return gradeAnalyticEndpoint.getInstructorActivityByMonth(schoolId, startDate, endDate);
    }

}
