package kg.kundoluk.school.controller.web.school;

import kg.kundoluk.school.constants.Constants;
import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.projection.SchoolDTO;
import kg.kundoluk.school.dto.school.SchoolBaseDto;
import kg.kundoluk.school.dto.school.SchoolCreateRequest;
import kg.kundoluk.school.endpoint.SchoolEndpoint;
import kg.kundoluk.school.model.enums.SchoolOrganizationType;
import kg.kundoluk.school.model.school.School;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kg.kundoluk.school.constants.Constants.CREATED;

@RestController
@RequestMapping("/api/web/v1/school")
public class SchoolController {
    private final SchoolEndpoint schoolEndpoint;

    public SchoolController(SchoolEndpoint schoolEndpoint) {
        this.schoolEndpoint = schoolEndpoint;
    }

    @GetMapping
    public List<School> list(@RequestParam(name = "isTest", defaultValue = "false") Boolean isTest) {
        return this.schoolEndpoint.list(isTest);
    }

    @GetMapping("/base-info")
    public List<SchoolBaseDto> listBaseInfo() {
        return this.schoolEndpoint.listBase();
    }

    @GetMapping("/{id}")
    public School get(@PathVariable("id") Long id) {
        return schoolEndpoint.get(id);
    }

    @GetMapping("/interface/{id}")
    public SchoolDTO getInterface(@PathVariable("id") Long id) {
        return schoolEndpoint.getSchoolInterface(id);
    }

    @GetMapping("/rayon/{rayonId}")
    public List<SchoolDTO> getByRayon(
            @PathVariable("rayonId") Long rayonId,
            @RequestParam(value = "schoolType", required = false) SchoolOrganizationType schoolOrganizationType
    ) {
        return schoolEndpoint.getByRayon(rayonId, schoolOrganizationType);
    }

    @GetMapping("/town/{townId}")
    public List<SchoolDTO> getByTown(@PathVariable("townId") Long townId) {
        return schoolEndpoint.getByTown(townId);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SchoolCreateRequest schoolCreateRequest) {
        schoolEndpoint.create(schoolCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, Constants.CREATED), HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody SchoolCreateRequest schoolCreateRequest, @PathVariable("id") School school) {
        schoolEndpoint.edit(schoolCreateRequest, school);
        return new ResponseEntity<>(new ApiResponse(true, Constants.UPDATED), HttpStatus.OK);
    }

    @DeleteMapping
    public Boolean delete(
            @RequestParam("id") Long id
    ) {
        this.schoolEndpoint.delete(id);
        return true;
    }
}
