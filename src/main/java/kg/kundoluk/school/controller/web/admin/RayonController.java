package kg.kundoluk.school.controller.web.admin;

import kg.kundoluk.school.model.location.Rayon;
import kg.kundoluk.school.service.references.RayonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/web/v1/rayon")
public class RayonController {

    private final RayonService rayonService;

    public RayonController(RayonService rayonService) {
        this.rayonService = rayonService;
    }

    @GetMapping
    public List<Rayon> list() {
        return this.rayonService.list();
    }
}
