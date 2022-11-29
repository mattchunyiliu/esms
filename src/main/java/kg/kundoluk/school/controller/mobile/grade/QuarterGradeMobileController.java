package kg.kundoluk.school.controller.mobile.grade;

import kg.kundoluk.school.constants.Constants;
import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.grade.*;
import kg.kundoluk.school.endpoint.QuarterGradeEndpoint;
import kg.kundoluk.school.model.grade.QuarterGrade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mobile/v1/quarter-grade")
public class QuarterGradeMobileController {
    private final QuarterGradeEndpoint quarterGradeEndpoint;

    public QuarterGradeMobileController(QuarterGradeEndpoint quarterGradeEndpoint) {
        this.quarterGradeEndpoint = quarterGradeEndpoint;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody QuarterGradeCreateRequest quarterGradeCreateRequest) {
        quarterGradeEndpoint.create(quarterGradeCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, Constants.CREATED), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody QuarterGradeUpdateRequest quarterGradeUpdateRequest, @PathVariable("id")QuarterGrade quarterGrade) {
        quarterGradeEndpoint.edit(quarterGradeUpdateRequest, quarterGrade);
        return new ResponseEntity<>(new ApiResponse(true, Constants.UPDATED), HttpStatus.OK);
    }

    @DeleteMapping
    public Boolean delete(
            @RequestParam("id") Long id
    ) {
        quarterGradeEndpoint.delete(id);
        return true;
    }

    @PostMapping("/fetch")
    public List<QuarterGradeMobileResponse> findAllByChronicle(@RequestBody QuarterGradeMobileResponseRequest gradeMobileCreateDayRequest) {
        return quarterGradeEndpoint.findAllByChronicle(gradeMobileCreateDayRequest);
    }

    @GetMapping("/student/{studentId}/chronicle/{chronicleId}")
    public List<QuarterGradeMobileResponse> findAllByStudentChronicle(@PathVariable("studentId") Long studentId, @PathVariable("chronicleId") Long chronicleId) {
        return quarterGradeEndpoint.findAllByStudentChronicle(studentId, chronicleId);
    }
}
