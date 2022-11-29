package kg.kundoluk.school.controller.web.school;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.shiftTime.ShiftTimeCreateRequest;
import kg.kundoluk.school.endpoint.ShiftTimeEndpoint;
import kg.kundoluk.school.model.school.ShiftTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/web/v1/school/shift/time")
public class ShiftTimeController {
    private final ShiftTimeEndpoint shiftTimeEndpoint;

    public ShiftTimeController(ShiftTimeEndpoint shiftTimeEndpoint) {
        this.shiftTimeEndpoint = shiftTimeEndpoint;
    }

    @GetMapping("/{id}")
    public ShiftTime get(@PathVariable("id") Long id) {
        return shiftTimeEndpoint.get(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ShiftTimeCreateRequest shiftTimeCreateRequest) {
        shiftTimeEndpoint.create(shiftTimeCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, "CREATED"), HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody ShiftTimeCreateRequest shiftCreateRequest, @PathVariable("id") ShiftTime shiftTime) {
        shiftTimeEndpoint.edit(shiftCreateRequest, shiftTime);
        return new ResponseEntity<>(new ApiResponse(true, "UPDATED"), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/bulk-operation")
    public ResponseEntity<?> createBulk(@RequestBody List<ShiftTimeCreateRequest> shiftTimeRequestList) {
        shiftTimeEndpoint.createBulk(shiftTimeRequestList);
        return new ResponseEntity<>(new ApiResponse(true, "CREATED"), HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @DeleteMapping
    public Boolean delete(
            @RequestParam("id") Long id
    ) {
        this.shiftTimeEndpoint.delete(id);
        return true;
    }

    @GetMapping("school/{schoolId}")
    public List<ShiftTimeCreateRequest> listBySchool(@PathVariable("schoolId") Long schoolId) {
        return this.shiftTimeEndpoint.listBySchool(schoolId);
    }
}
