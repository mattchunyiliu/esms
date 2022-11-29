package kg.kundoluk.school.controller.web.admin;

import kg.kundoluk.school.model.location.Region;
import kg.kundoluk.school.service.references.RegionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/web/v1/region")
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    public List<Region> list() {
        return this.regionService.list();
    }
}
