package kg.kundoluk.school.controller.web.school;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.projection.SchoolClassBaseDTO;
import kg.kundoluk.school.dto.projection.SchoolClassDTO;
import kg.kundoluk.school.dto.schoolClass.ClassCreateRequest;
import kg.kundoluk.school.dto.schoolClass.SchoolClassResource;
import kg.kundoluk.school.endpoint.SchoolClassEndpoint;
import kg.kundoluk.school.mapper.schoolClass.SchoolClassMapper;
import kg.kundoluk.school.model.school.SchoolClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/web/v1/class")
public class SchoolClassController {

    private final SchoolClassEndpoint schoolClassEndpoint;
    private final SchoolClassMapper schoolClassMapper;

    public SchoolClassController(SchoolClassEndpoint schoolClassEndpoint, SchoolClassMapper schoolClassMapper) {
        this.schoolClassEndpoint = schoolClassEndpoint;
        this.schoolClassMapper = schoolClassMapper;
    }

    @GetMapping("/{id}")
    public SchoolClassDTO get(@PathVariable("id") Long id) {
        return schoolClassEndpoint.get(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ClassCreateRequest classCreateRequest) {
        schoolClassEndpoint.create(classCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, "CREATED"), HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody ClassCreateRequest classCreateRequest, @PathVariable("id") SchoolClass schoolClass) {
        schoolClassEndpoint.edit(classCreateRequest, schoolClass);
        return new ResponseEntity<>(new ApiResponse(true, "UPDATED"), HttpStatus.OK);
    }

    @DeleteMapping
    public Boolean delete(
            @RequestParam("id") Long id,
            @RequestParam("schoolId") Long schoolId
    ) {
        this.schoolClassEndpoint.delete(id, schoolId);
        return true;
    }

    @GetMapping("school/{schoolId}")
    public List<SchoolClassResource> findAllBySchool(@PathVariable("schoolId") Long schoolId) {
        return this.schoolClassEndpoint.findAllBySchool(schoolId).stream().map(schoolClassMapper::toSchoolClassResource).collect(Collectors.toList());
    }

    @GetMapping("interface/school/{schoolId:[\\d]+}")
    public List<SchoolClassDTO> listBySchool(@PathVariable("schoolId") Long schoolId) {
        return this.schoolClassEndpoint.listBySchool(schoolId);
    }

    @GetMapping("instructor/{instructorId}")
    public List<SchoolClassBaseDTO> listByInstructor(@PathVariable("instructorId") Long instructorId) {
        return this.schoolClassEndpoint.listByPerson(instructorId);
    }
}
