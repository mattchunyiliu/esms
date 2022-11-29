package kg.kundoluk.school.controller.web.section;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.section.SectionCreateRequest;
import kg.kundoluk.school.dto.section.SectionInstructorViewResponse;
import kg.kundoluk.school.dto.section.SectionUpdateRequest;
import kg.kundoluk.school.endpoint.SectionEndpoint;
import kg.kundoluk.school.model.section.Section;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/web/v1/school/section")
public class SectionController {
    private final SectionEndpoint sectionEndpoint;

    public SectionController(SectionEndpoint sectionEndpoint) {
        this.sectionEndpoint = sectionEndpoint;
    }

    @GetMapping("/{id}")
    public Section get(@PathVariable("id") Long id) {
        return sectionEndpoint.get(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SectionCreateRequest sectionCreateRequest) {
        Section section = sectionEndpoint.create(sectionCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, section.getId().toString()), HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody SectionUpdateRequest sectionUpdateRequest, @PathVariable("id") Section section) {
        sectionEndpoint.edit(sectionUpdateRequest, section);
        return new ResponseEntity<>(new ApiResponse(true, "UPDATED"), HttpStatus.OK);
    }

    @DeleteMapping
    public Boolean delete(
            @RequestParam("id") Long id
    ) {
        this.sectionEndpoint.delete(id);
        return true;
    }

    @GetMapping("school/{schoolId}")
    public List<SectionInstructorViewResponse> listBySchool(@PathVariable("schoolId") Long schoolId) {
        return this.sectionEndpoint.listBySchool(schoolId);
    }
}
