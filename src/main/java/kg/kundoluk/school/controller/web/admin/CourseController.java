package kg.kundoluk.school.controller.web.admin;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.course.CourseRequest;
import kg.kundoluk.school.model.references.Course;
import kg.kundoluk.school.service.references.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kg.kundoluk.school.constants.Constants.CREATED;

@RestController
@RequestMapping("/api/web/v1/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CourseRequest course) {
        courseService.create(course);
        return new ResponseEntity<>(new ApiResponse(true, CREATED), HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody CourseRequest dst, @PathVariable("id") Course src) {
        courseService.edit(src, dst);
        return new ResponseEntity<>(new ApiResponse(true, CREATED), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Course get(@PathVariable("id") Long id) {
        return courseService.findById(id);
    }

    @GetMapping
    public List<Course> list() {
        return this.courseService.list();
    }
}
