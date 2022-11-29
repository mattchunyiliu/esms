package kg.kundoluk.school.controller.web.student;

import kg.kundoluk.school.constants.Constants;
import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.projection.GradeParentCheckResponse;
import kg.kundoluk.school.dto.projection.StudentViewDTO;
import kg.kundoluk.school.dto.student.parent.GradeParentCheckCreateRequest;
import kg.kundoluk.school.endpoint.GradeParentCheckEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/web/v1/grade/parent/check")
public class GradeParentCheckController {

    private final GradeParentCheckEndpoint gradeParentCheckEndpoint;

    public GradeParentCheckController(GradeParentCheckEndpoint gradeParentCheckEndpoint) {
        this.gradeParentCheckEndpoint = gradeParentCheckEndpoint;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody GradeParentCheckCreateRequest gradeParentCheckCreateRequest)  {
        gradeParentCheckEndpoint.create(gradeParentCheckCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, Constants.CREATED), HttpStatus.OK);
    }

    @GetMapping("/class/{classId}")
    public List<GradeParentCheckResponse> listByClass(
            @PathVariable("classId") Long classId,
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate,
            @RequestParam(value = "studentId", required = false) Long studentId
    ) {
        return this.gradeParentCheckEndpoint.getList(studentId, classId, startDate, endDate);
    }
}
