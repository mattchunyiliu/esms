package kg.kundoluk.school.controller.analytic;

import kg.kundoluk.school.constants.Constants;
import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.report.MoveReportDto;
import kg.kundoluk.school.dto.report.MoveReportProjection;
import kg.kundoluk.school.model.MoveReport;
import kg.kundoluk.school.service.report.MoveReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/analytic/v1/move/analytic")
public class StudentMoveAnalyticController {
    private final MoveReportService moveReportService;

    public StudentMoveAnalyticController(MoveReportService moveReportService) {
        this.moveReportService = moveReportService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody MoveReportDto moveReportDto) {
        moveReportService.create(moveReportDto);
        return new ResponseEntity<>(new ApiResponse(true, Constants.CREATED), HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody MoveReportDto moveReportDto, @PathVariable("id") MoveReport moveReport) {
        moveReportService.update(moveReport, moveReportDto);
        return new ResponseEntity<>(new ApiResponse(true, Constants.UPDATED), HttpStatus.OK);
    }

    @GetMapping("/quarter/{quarterId}")
    public List<MoveReportProjection> get(@PathVariable("quarterId") Long quarterId, @RequestParam(name = "classId", required = false) Long classId) {
        return moveReportService.findAllByQuarter(quarterId, classId);
    }
}
