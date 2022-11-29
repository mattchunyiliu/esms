package kg.kundoluk.school.controller.web.student;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.report.ClassInstructorCourseDTO;
import kg.kundoluk.school.dto.student.courses.StudentCourseCreateRequest;
import kg.kundoluk.school.dto.student.courses.StudentCourseResponse;
import kg.kundoluk.school.endpoint.StudentCourseEndpoint;
import kg.kundoluk.school.exception.AlreadyExistException;
import kg.kundoluk.school.exception.ConstraintMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/web/v1/student/course")
public class StudentCourseController {
    private final StudentCourseEndpoint studentCourseEndpoint;

    public StudentCourseController(StudentCourseEndpoint studentCourseEndpoint) {
        this.studentCourseEndpoint = studentCourseEndpoint;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(
            @Valid @RequestBody StudentCourseCreateRequest studentCourseCreateRequest
    ) {
        studentCourseEndpoint.create(studentCourseCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, "CREATED"), HttpStatus.OK);
    }

    @PostMapping("/bulk-create")
    public ResponseEntity<?> createBulk(
            @Valid @RequestBody List<StudentCourseCreateRequest> studentCourseCreateRequestList
    ) throws AlreadyExistException {
        studentCourseEndpoint.createBulk(studentCourseCreateRequestList);
        return new ResponseEntity<>(new ApiResponse(true, "CREATED"), HttpStatus.OK);
    }

    @DeleteMapping("/bulk-delete")
    public ResponseEntity<?> deleteBulk(
            @Valid @RequestBody StudentCourseCreateRequest studentCourseCreateRequest
    ) {
        studentCourseEndpoint.deleteBulk(studentCourseCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, "DELETED"), HttpStatus.OK);
    }

    @GetMapping("/student/{studentId}/chronicle/{chronicleId}")
    public List<StudentCourseResponse> findAllByStudent(@PathVariable("studentId") Long studentId, @PathVariable("chronicleId") Long chronicleId) {
        return studentCourseEndpoint.findAllByStudent(studentId, chronicleId);
    }

    @GetMapping("/class/{classId}/chronicle/{chronicleId}")
    public List<StudentCourseResponse> findAllByClass(@PathVariable("classId") Long classId, @PathVariable("chronicleId") Long chronicleId) {
        return studentCourseEndpoint.findAllByClass(classId, chronicleId);
    }

    @GetMapping("/class-course-list")
    public List<ClassInstructorCourseDTO> getClassCourseList(@RequestParam("classId") Long classId, @RequestParam("chronicleId") Long chronicleId) {
        return studentCourseEndpoint.getClassCourseList(classId, chronicleId);
    }

    @DeleteMapping
    public Boolean delete(
            @RequestParam("id") Long id
    ) throws ConstraintMappingException {
        this.studentCourseEndpoint.delete(id);
        return true;
    }
}
