package kg.kundoluk.school.controller.web.admin;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.projection.StudentViewMobileDTO;
import kg.kundoluk.school.exception.AlreadyExistException;
import kg.kundoluk.school.service.async.StudentAdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/web/v1/admin/student")
public class StudentAdminController {
    private final StudentAdminService studentAdminService;

    public StudentAdminController(StudentAdminService studentAdminService) {
        this.studentAdminService = studentAdminService;
    }

    @PostMapping("/level-change")
    public ResponseEntity<?> levelChange(@RequestParam("schoolId") Long schoolId,@RequestParam(value = "classId", required = false) Long classId, @RequestParam("chronicleId") Long chronicleId, @RequestParam("isRaise") Boolean isRaise) throws AlreadyExistException {
        studentAdminService.changeLevel(schoolId, classId, chronicleId, isRaise);
        return new ResponseEntity<>(new ApiResponse(true, "UPDATED"), HttpStatus.OK);
    }

    @GetMapping("/class/{classId}")
    public List<StudentViewMobileDTO> getByClass(@PathVariable("classId") Long classId) {
        return studentAdminService.getByClass(classId);
    }

    @PostMapping("/generate-course")
    public ResponseEntity<?> generateCourse(
            @RequestParam("classId") Long classId,
            @RequestParam("chronicleId") Long chronicleId
    ) throws AlreadyExistException {
        studentAdminService.generateClassCourses(classId, chronicleId);
        return new ResponseEntity<>(new ApiResponse(true, "UPDATED"), HttpStatus.OK);
    }
}
