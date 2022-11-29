package kg.kundoluk.school.controller.web.school;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.projection.SchoolPresetDTO;
import kg.kundoluk.school.dto.school.SchoolPresetDto;
import kg.kundoluk.school.model.school.SchoolPreset;
import kg.kundoluk.school.service.school.SchoolPresetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/web/v1/school-preset")
public class SchoolPresetController {
    private final SchoolPresetService schoolPresetService;

    public SchoolPresetController(SchoolPresetService schoolPresetService) {
        this.schoolPresetService = schoolPresetService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> create(
            @RequestBody SchoolPresetDto schoolPresetDto
    ) {
        schoolPresetService.create(schoolPresetDto);
        return new ResponseEntity<>(new ApiResponse(true, "School Preset has been added"), HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> edit(
            @RequestBody SchoolPresetDto schoolPresetDto,
            @PathVariable(name = "id") SchoolPreset schoolPreset
    ) {
        schoolPresetService.update(schoolPreset, schoolPresetDto);
        return new ResponseEntity<>(new ApiResponse(true, "School has been updated"), HttpStatus.OK);
    }

    @PostMapping("/check")
    public SchoolPresetDTO check(
            @RequestParam("schoolId") Long schoolId,
            @RequestParam("chronicleId") Long chronicleId
    ) {
        return schoolPresetService.isExist(schoolId, chronicleId);
    }

    @GetMapping("/chronicle/{chronicle_id}")
    public List<SchoolPresetDTO> listByChronicle(
            @PathVariable("chronicle_id") Long chronicleId
    ) {
        return schoolPresetService.listByChronicle(chronicleId);
    }
}
