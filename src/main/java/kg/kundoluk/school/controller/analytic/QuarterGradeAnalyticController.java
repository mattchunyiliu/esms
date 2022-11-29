package kg.kundoluk.school.controller.analytic;

import kg.kundoluk.school.dto.projection.GradeClassGroupAverage;
import kg.kundoluk.school.dto.projection.QuarterGradeGroupMarkDTO;
import kg.kundoluk.school.dto.projection.QuarterGradeStudentAnalyticDTO;
import kg.kundoluk.school.dto.projection.QuarterGradeStudentGroupMarkDTO;
import kg.kundoluk.school.dto.report.ClassQuarterGradeReportResponse;
import kg.kundoluk.school.endpoint.QuarterGradeAnalyticEndpoint;
import kg.kundoluk.school.model.enums.QuarterGradeMarkType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/analytic/v1/quarter-grade/analytic")
public class QuarterGradeAnalyticController {
    private final QuarterGradeAnalyticEndpoint quarterGradeAnalyticEndpoint;

    public QuarterGradeAnalyticController(QuarterGradeAnalyticEndpoint quarterGradeAnalyticEndpoint) {
        this.quarterGradeAnalyticEndpoint = quarterGradeAnalyticEndpoint;
    }

    @GetMapping("/grade-sheet")
    public List<QuarterGradeStudentAnalyticDTO> getGradeSheetAnalytic(
            @RequestParam(value = "classId") Long classId,
            @RequestParam(value = "quarterId", required = false) Long quarterId,
            @RequestParam(value = "courseId", required = false) Long courseId,
            @RequestParam(value = "gradeType", required = false) QuarterGradeMarkType quarterGradeMarkType,
            @RequestParam(value = "chronicleId") Long chronicleId
    ) {
        return quarterGradeAnalyticEndpoint.getAllStudentAnalytic(classId, quarterId, courseId, quarterGradeMarkType, chronicleId);
    }

    @GetMapping("/class-performance")
    public ClassQuarterGradeReportResponse getClassPerformance(
            @RequestParam(value = "classId") Long classId,
            @RequestParam(value = "quarterId") Long quarterId,
            @RequestParam(value = "gradeType") QuarterGradeMarkType quarterGradeMarkType,
            @RequestParam(value = "chronicleId") Long chronicleId
    ) {
        return quarterGradeAnalyticEndpoint.getClassPerformance(classId, quarterId, quarterGradeMarkType, chronicleId);
    }

    @GetMapping("/count")
    public List<QuarterGradeGroupMarkDTO> getExcellentCountGroup(
            @RequestParam(value = "schoolId") Long schoolId,
            @RequestParam(value = "classId", required = false) Long classId,
            @RequestParam(value = "quarterId") Long quarterId
    ) {
        return quarterGradeAnalyticEndpoint.getStudentGroupByMark(schoolId, classId, quarterId);
    }

    @GetMapping("/list-excellence")
    public List<QuarterGradeStudentGroupMarkDTO> getExcellentListGroup(
            @RequestParam(value = "schoolId") Long schoolId,
            @RequestParam(value = "classId", required = false) Long classId,
            @RequestParam(value = "quarterId") Long quarterId
    ) {
        return quarterGradeAnalyticEndpoint.getStudentsGroupByMark(schoolId, classId, quarterId);
    }

    @GetMapping("/average/school/{schoolId}")
    public List<GradeClassGroupAverage> getAverageByClass(
            @PathVariable(value = "schoolId") Long schoolId,
            @RequestParam(value = "quarterId") Long quarterId
    ) {
        return quarterGradeAnalyticEndpoint.getClassAverageGrade(schoolId, quarterId);
    }
}
