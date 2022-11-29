package kg.kundoluk.school.controller.mobile.grade;

import kg.kundoluk.school.constants.Constants;
import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.grade.*;
import kg.kundoluk.school.dto.projection.GradeDTO;
import kg.kundoluk.school.endpoint.GradeEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mobile/v1/grade")
public class GradeMobileController {
    private final GradeEndpoint gradeEndpoint;

    public GradeMobileController(GradeEndpoint gradeEndpoint) {
        this.gradeEndpoint = gradeEndpoint;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping("/bulk-create")
    public ResponseEntity<?> createBulk(@RequestBody GradeMobileCreateDayRequest gradeMobileCreateDayRequest) {
        gradeEndpoint.createBulkMobile(gradeMobileCreateDayRequest);
        return new ResponseEntity<>(new ApiResponse(true, Constants.CREATED), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping("/bulk-edit")
    public ResponseEntity<?> editBulk(@RequestBody List<GradeMobileUpdateRequest> gradeMobileUpdateRequests) {
        gradeEndpoint.editBulkMobile(gradeMobileUpdateRequests);
        return new ResponseEntity<>(new ApiResponse(true, Constants.UPDATED), HttpStatus.OK);
    }

    @DeleteMapping
    public Boolean deleteById(
            @RequestParam("id") Long id
    ) {
        gradeEndpoint.deleteById(id);
        return true;
    }

    @DeleteMapping("topic/{topicId}")
    public Boolean deleteByTopic(
            @PathVariable("topicId") Long topicId
    ) {
        gradeEndpoint.deleteByTopic(topicId);
        return true;
    }

    @PostMapping("/day-fetch")
    public List<GradeMobileResponse> dayFetch(@RequestBody GradeMobileResponseRequest gradeMobileCreateDayRequest) {
        return gradeEndpoint.dayFetch(gradeMobileCreateDayRequest);
    }

    @PostMapping("/month-fetch")
    public List<GradeMobileResponse> monthFetch(@RequestBody GradeMobileResponseDateRequest gradeMobileResponseDateRequest) {
        return gradeEndpoint.monthFetch(gradeMobileResponseDateRequest);
    }

    @PostMapping("/student-fetch")
    public List<GradeMobileResponse> studentFetch(@RequestBody GradeMobileResponseStudentRequest gradeMobileResponseStudentRequest) {
        return gradeEndpoint.studentFetch(gradeMobileResponseStudentRequest);
    }

    @GetMapping("/topic/{topicId}")
    public List<GradeMobileResponse> findAllByTopic(@PathVariable("topicId") Long topicId) {
        return gradeEndpoint.findAllByTopic(topicId);
    }

    @GetMapping("/shift-time/{shiftTimeId}")
    public List<GradeMobileResponse> findAllByShiftTime(@PathVariable("shiftTimeId") Long shiftTimeId) {
        return gradeEndpoint.findAllByShiftTime(shiftTimeId);
    }
}
