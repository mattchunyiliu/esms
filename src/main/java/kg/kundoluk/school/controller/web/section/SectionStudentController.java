package kg.kundoluk.school.controller.web.section;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.section.SectionStudentRequest;
import kg.kundoluk.school.dto.section.SectionStudentResponse;
import kg.kundoluk.school.dto.student.StudentBasicInfo;
import kg.kundoluk.school.model.section.SectionStudent;
import kg.kundoluk.school.service.section.SectionStudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/web/v1/school/section/student")
public class SectionStudentController {
    private final SectionStudentService sectionStudentService;

    public SectionStudentController(SectionStudentService sectionStudentService) {
        this.sectionStudentService = sectionStudentService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SectionStudentRequest sectionStudentRequest) {
        SectionStudent section = sectionStudentService.create(sectionStudentRequest);
        return new ResponseEntity<>(new ApiResponse(true, section.getId().toString()), HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody SectionStudentRequest sectionStudentRequest, @PathVariable("id") SectionStudent sectionStudent) {
        sectionStudentService.edit(sectionStudentRequest, sectionStudent);
        return new ResponseEntity<>(new ApiResponse(true, "UPDATED"), HttpStatus.OK);
    }

    @DeleteMapping
    public Boolean delete(
            @RequestParam("id") Long id
    ) {
        this.sectionStudentService.delete(id);
        return true;
    }

    @GetMapping("section/{sectionInstructorId}")
    public List<StudentBasicInfo> listBySection(@PathVariable("sectionInstructorId") Long sectionId) {
        return this.sectionStudentService.listBySectionInstructor(sectionId);
    }

    @GetMapping("{studentId}/chronicle/{chronicleId}")
    public List<SectionStudentResponse> listByStudent(@PathVariable("studentId") Long studentId, @PathVariable("chronicleId") Long chronicleId) {
        return this.sectionStudentService.listByStudent(studentId, chronicleId);
    }
}
