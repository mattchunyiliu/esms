package kg.kundoluk.school.controller.mobile.student;

import kg.kundoluk.school.dto.projection.StudentCourseViewDTO;
import kg.kundoluk.school.dto.student.courses.StudentCourseDto;
import kg.kundoluk.school.dto.student.courses.StudentCourseMobileResponse;
import kg.kundoluk.school.dto.student.courses.StudentCourseResponse;
import kg.kundoluk.school.endpoint.StudentCourseEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/mobile/v1/student/course")
public class StudentCourseMobileController {
    private final StudentCourseEndpoint studentCourseEndpoint;

    public StudentCourseMobileController(StudentCourseEndpoint studentCourseEndpoint) {
        this.studentCourseEndpoint = studentCourseEndpoint;
    }

    @GetMapping("/person/{personId}/class/{classId}/course/{courseId}/chronicle/{chronicleId}")
    public List<StudentCourseViewDTO> findAllByPersonCourse(@PathVariable("personId") Long personId, @PathVariable("classId") Long classId, @PathVariable("courseId") Long courseId, @PathVariable("chronicleId") Long chronicleId) {
        return studentCourseEndpoint.findAllByPersonCourseClassInterface(personId, classId, courseId, chronicleId);
    }

    @GetMapping("/student/{studentId}/chronicle/{chronicleId}")
    public List<StudentCourseMobileResponse> findAllByStudent(@PathVariable("studentId") Long studentId, @PathVariable("chronicleId") Long chronicleId) {
        return studentCourseEndpoint.findAllByStudentMobile(studentId, chronicleId);
    }
}
