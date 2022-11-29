package kg.kundoluk.school.controller.web.admin;

import kg.kundoluk.school.constants.Constants;
import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.reference.TownDto;
import kg.kundoluk.school.model.location.Town;
import kg.kundoluk.school.service.references.TownService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/web/v1/town")
public class TownController {

    private final TownService townService;

    public TownController(TownService townService) {
        this.townService = townService;
    }

    @GetMapping
    public List<Town> list() {
        return this.townService.list();
    }

    @GetMapping("/rayon/{rayonId}")
    public List<Town> listByRayon(@PathVariable("rayonId") Long rayonId) {
        return this.townService.listByRayon(rayonId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody TownDto townDto) {
        townService.create(townDto);
        return new ResponseEntity<>(new ApiResponse(true, Constants.CREATED), HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody TownDto townDto, @PathVariable("id") Town town) {
        townService.edit(townDto, town);
        return new ResponseEntity<>(new ApiResponse(true, Constants.UPDATED), HttpStatus.OK);
    }
}
