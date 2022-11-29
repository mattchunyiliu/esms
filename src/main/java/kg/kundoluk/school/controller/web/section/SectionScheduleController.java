package kg.kundoluk.school.controller.web.section;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.projection.SectionScheduleDTO;
import kg.kundoluk.school.dto.section.SectionScheduleRequest;
import kg.kundoluk.school.model.section.SectionSchedule;
import kg.kundoluk.school.service.section.SectionScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/web/v1/school/section/schedule")
public class SectionScheduleController {
    private final SectionScheduleService sectionScheduleService;

    public SectionScheduleController(SectionScheduleService sectionScheduleService) {
        this.sectionScheduleService = sectionScheduleService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SectionScheduleRequest sectionScheduleRequest) {
        SectionSchedule section = sectionScheduleService.create(sectionScheduleRequest);
        return new ResponseEntity<>(new ApiResponse(true, section.getId().toString()), HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody SectionScheduleRequest sectionScheduleRequest, @PathVariable("id") SectionSchedule sectionSchedule) {
        sectionScheduleService.edit(sectionScheduleRequest, sectionSchedule);
        return new ResponseEntity<>(new ApiResponse(true, "UPDATED"), HttpStatus.OK);
    }

    @DeleteMapping
    public Boolean delete(
            @RequestParam("id") Long id
    ) {
        this.sectionScheduleService.delete(id);
        return true;
    }

    @GetMapping("/{sectionInstructorId}")
    public List<SectionScheduleDTO> listBySection(@PathVariable("sectionInstructorId") Long sectionInstructorId) {
        return this.sectionScheduleService.listBySectionInstructor(sectionInstructorId);
    }
}
