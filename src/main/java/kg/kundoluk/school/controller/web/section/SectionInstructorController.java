package kg.kundoluk.school.controller.web.section;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.section.*;
import kg.kundoluk.school.endpoint.SectionInstructorScheduleEndpoint;
import kg.kundoluk.school.model.section.SectionInstructor;
import kg.kundoluk.school.service.section.SectionInstructorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/web/v1/school/section/instructor")
public class SectionInstructorController {
    private final SectionInstructorScheduleEndpoint sectionInstructorScheduleEndpoint;

    public SectionInstructorController(SectionInstructorScheduleEndpoint sectionInstructorScheduleEndpoint) {
        this.sectionInstructorScheduleEndpoint = sectionInstructorScheduleEndpoint;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SectionInstructorScheduleCreateRequest sectionInstructorCreateRequest) {
        SectionInstructor section = sectionInstructorScheduleEndpoint.create(sectionInstructorCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, section.getId().toString()), HttpStatus.OK);
    }

    @PostMapping("/edit/{sectionInstructorId}")
    public ResponseEntity<?> edit(@RequestBody SectionInstructorScheduleUpdateRequest sectionInstructorScheduleUpdateRequest, @PathVariable("sectionInstructorId") SectionInstructor sectionInstructor) {
        sectionInstructorScheduleEndpoint.edit(sectionInstructorScheduleUpdateRequest, sectionInstructor);
        return new ResponseEntity<>(new ApiResponse(true, "UPDATED"), HttpStatus.OK);
    }

    @DeleteMapping
    public Boolean delete(
            @RequestParam("sectionInstructorId") Long sectionInstructorId
    ) {
        this.sectionInstructorScheduleEndpoint.delete(sectionInstructorId);
        return true;
    }

    @GetMapping("school/{schoolId}")
    public List<SectionInstructorScheduleResponse> listBySchool(@PathVariable("schoolId") Long schoolId) {
        return this.sectionInstructorScheduleEndpoint.getBySchool(schoolId);
    }

    @GetMapping("section/{sectionId}")
    public List<SectionInstructorScheduleResponse> listBySection(@PathVariable("sectionId") Long sectionId) {
        return this.sectionInstructorScheduleEndpoint.getBySection(sectionId);
    }

    @GetMapping("{instructorId}")
    public List<SectionInstructorResponse> listByInstructor(@PathVariable("instructorId") Long instructorId) {
        return this.sectionInstructorScheduleEndpoint.listByInstructor(instructorId);
    }

    @GetMapping("section-instructor/{sectionInstructorId}")
    public SectionInstructorResponse getBySectionInstructor(@PathVariable("sectionInstructorId") Long sectionInstructorId) {
        return this.sectionInstructorScheduleEndpoint.getBySectionInstructor(sectionInstructorId);
    }
}
