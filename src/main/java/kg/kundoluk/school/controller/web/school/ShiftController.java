package kg.kundoluk.school.controller.web.school;

import io.swagger.annotations.ApiOperation;
import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.shift.ShiftCreateRequest;
import kg.kundoluk.school.endpoint.ShiftEndpoint;
import kg.kundoluk.school.model.school.Shift;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/web/v1/school/shift")
public class ShiftController {

    private final ShiftEndpoint shiftEndpoint;

    public ShiftController(ShiftEndpoint shiftEndpoint) {
        this.shiftEndpoint = shiftEndpoint;
    }

    @GetMapping("/{id}")
    public Shift get(@PathVariable("id") Long id) {
        return shiftEndpoint.get(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @ApiOperation(value = "Create shift", notes = "This method creates a new shift")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ShiftCreateRequest shiftCreateRequest) {
        Shift shift = shiftEndpoint.create(shiftCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, shift.getId().toString()), HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody ShiftCreateRequest shiftCreateRequest, @PathVariable("id") Shift shift) {
        shiftEndpoint.edit(shiftCreateRequest, shift);
        return new ResponseEntity<>(new ApiResponse(true, "UPDATED"), HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @DeleteMapping
    public Boolean delete(
            @RequestParam("id") Long id
    ) {
        this.shiftEndpoint.delete(id);
        return true;
    }

    @GetMapping("school/{schoolId}")
    public List<Shift> listBySchool(@PathVariable("schoolId") Long schoolId) {
        return this.shiftEndpoint.listBySchool(schoolId);
    }
}
