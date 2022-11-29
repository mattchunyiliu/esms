package kg.kundoluk.school.controller.analytic;

import kg.kundoluk.school.dto.grade.AttendanceCountDto;
import kg.kundoluk.school.dto.projection.*;
import kg.kundoluk.school.endpoint.GradeAnalyticEndpoint;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/analytic/v1/attendance/analytic")
public class AttendanceAnalyticController {
    private final GradeAnalyticEndpoint gradeAnalyticEndpoint;

    public AttendanceAnalyticController(GradeAnalyticEndpoint gradeAnalyticEndpoint) {
        this.gradeAnalyticEndpoint = gradeAnalyticEndpoint;
    }

    @GetMapping("/student/{studentId}")
    public AttendanceCountDto getStudentAttendance(
            @PathVariable("studentId") Long studentId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        return gradeAnalyticEndpoint.getStudentAttendanceCount(studentId, startDate, endDate);
    }

    @GetMapping("/attendance-count/rayon/{rayonId}")
    public List<GradeSchoolGroupAnalytic> getByRayonAttendanceCount(
            @PathVariable("rayonId") Long rayonId,
            @RequestParam(value = "townId", required = false) Long townId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        return gradeAnalyticEndpoint.getSchoolAttendanceCount(rayonId, townId, startDate, endDate);
    }

    @GetMapping("/month/reason-count/school/{schoolId}")
    public List<AttendanceReasonCountMonth> getGroupAttendanceReasonMonth(
            @PathVariable("schoolId") Long schoolId,
            @RequestParam(value = "classId", required = false) Long classId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        return gradeAnalyticEndpoint.getAttendanceGroupReasonMonth(schoolId, classId, startDate, endDate);
    }

    @GetMapping("/quarter/reason-count/school/{schoolId}")
    public List<AttendanceReasonCountClass> getGroupAttendanceReasonQuarter(
            @PathVariable("schoolId") Long schoolId,
            @RequestParam("quarterId") Long quarterId
    ) {
        return gradeAnalyticEndpoint.getAttendanceGroupReasonQuarter(schoolId, quarterId);
    }

    @GetMapping("/month/reason-count/class/{classId}")
    public List<AttendanceReasonCountStudentMonth> getGroupAttendanceReasonClassMonth(
            @PathVariable("classId") Long classId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        return gradeAnalyticEndpoint.getAttendanceGroupReasonClassMonth(classId, startDate, endDate);
    }

    @GetMapping("/day/reason-count/school/{schoolId}")
    public List<AttendanceReasonCountDay> getGroupAttendanceReasonDay(
            @PathVariable("schoolId") Long schoolId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        return gradeAnalyticEndpoint.getAttendanceGroupReasonDay(schoolId, startDate, endDate);
    }

    @GetMapping("/card")
    public List<AnalyticSchoolCount> getCardAttendanceBySchool(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        return gradeAnalyticEndpoint.getCardAttendanceCountBySchool(startDate, endDate);
    }

}
