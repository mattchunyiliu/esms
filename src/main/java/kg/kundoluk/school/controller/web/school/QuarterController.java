package kg.kundoluk.school.controller.web.school;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.quarter.QuarterCreateRequest;
import kg.kundoluk.school.dto.quarter.QuarterDto;
import kg.kundoluk.school.dto.quarter.QuarterShortResponse;
import kg.kundoluk.school.endpoint.QuarterEndpoint;
import kg.kundoluk.school.model.school.Quarter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/web/v1/quarter")
public class QuarterController {
    private final QuarterEndpoint quarterEndpoint;

    public QuarterController(QuarterEndpoint quarterEndpoint) {
        this.quarterEndpoint = quarterEndpoint;
    }

    @GetMapping("/{id}")
    public QuarterShortResponse get(@PathVariable("id") Long id) {
        return quarterEndpoint.get(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody QuarterCreateRequest quarterCreateRequest) {
        quarterEndpoint.create(quarterCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, "CREATED"), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/bulk-operation")
    public ResponseEntity<?> createBulk(@RequestBody QuarterDto quarterDto) {
        quarterEndpoint.createBulk(quarterDto);
        return new ResponseEntity<>(new ApiResponse(true, "CREATED"), HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody QuarterCreateRequest quarterCreateRequest, @PathVariable("id") Quarter quarter) {
        quarterEndpoint.edit(quarterCreateRequest, quarter);
        return new ResponseEntity<>(new ApiResponse(true, "UPDATED"), HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @DeleteMapping
    public Boolean delete(
            @RequestParam("id") Long id
    ) {
        this.quarterEndpoint.delete(id);
        return true;
    }

    @GetMapping("school/{schoolId}/chronicle/{chronicleId}")
    public List<QuarterShortResponse> listBySchool(@PathVariable("schoolId") Long schoolId, @PathVariable("chronicleId") Long chronicleId) {
        return this.quarterEndpoint.listBySchoolChronicle(schoolId, chronicleId);
    }

}
