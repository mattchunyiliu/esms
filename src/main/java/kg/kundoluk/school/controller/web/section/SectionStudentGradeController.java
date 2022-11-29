package kg.kundoluk.school.controller.web.section;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.section.SectionStudentGradeCreateRequest;
import kg.kundoluk.school.dto.section.SectionStudentGradeResponse;
import kg.kundoluk.school.dto.section.SectionStudentGradeUpdateRequest;
import kg.kundoluk.school.dto.section.SectionStudentRequest;
import kg.kundoluk.school.dto.student.StudentBasicInfo;
import kg.kundoluk.school.model.section.SectionStudent;
import kg.kundoluk.school.model.section.SectionStudentGrade;
import kg.kundoluk.school.service.section.SectionStudentGradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/web/v1/school/section/student/grade")
public class SectionStudentGradeController {
    private final SectionStudentGradeService sectionStudentGradeService;

    public SectionStudentGradeController(SectionStudentGradeService sectionStudentGradeService) {
        this.sectionStudentGradeService = sectionStudentGradeService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SectionStudentGradeCreateRequest sectionStudentGradeCreateRequest) {
        sectionStudentGradeService.create(sectionStudentGradeCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, "CREATED"), HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody SectionStudentGradeUpdateRequest sectionStudentGradeCreateRequest, @PathVariable("id") SectionStudentGrade sectionStudentGrade) {
        sectionStudentGradeService.update(sectionStudentGradeCreateRequest, sectionStudentGrade);
        return new ResponseEntity<>(new ApiResponse(true, "UPDATED"), HttpStatus.OK);
    }

    @DeleteMapping
    public Boolean delete(
            @RequestParam("id") Long id
    ) {
        this.sectionStudentGradeService.delete(id);
        return true;
    }

    @GetMapping("section/{sectionInstructorId}")
    public List<SectionStudentGradeResponse> listBySection(
            @PathVariable("sectionInstructorId") Long sectionId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        return this.sectionStudentGradeService.listBySectionInstructor(sectionId, startDate, endDate);
    }

    @GetMapping("/student/{studentId}/section/{sectionInstructorId}")
    public List<SectionStudentGradeResponse> listByStudent(
            @PathVariable("studentId") Long studentId,
            @PathVariable("sectionInstructorId") Long sectionId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        return this.sectionStudentGradeService.listBySectionStudent(studentId, sectionId, startDate, endDate);
    }
}
