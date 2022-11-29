package kg.kundoluk.school.controller.mobile.student;

import kg.kundoluk.school.dto.projection.StudentViewMobileDTO;
import kg.kundoluk.school.endpoint.StudentMobileEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/mobile/v1/student")
public class StudentMobileController {
    private final StudentMobileEndpoint studentMobileEndpoint;

    public StudentMobileController(StudentMobileEndpoint studentMobileEndpoint) {
        this.studentMobileEndpoint = studentMobileEndpoint;
    }

    @GetMapping("/class/{classId}")
    public List<StudentViewMobileDTO> listByClass(@PathVariable("classId") Long id) {
        return this.studentMobileEndpoint.listByClass(id);
    }
}
