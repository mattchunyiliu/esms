package kg.kundoluk.school.controller.web.admin;

import kg.kundoluk.school.model.references.ChronicleYear;
import kg.kundoluk.school.service.references.ChronicleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/web/v1/chronicle")
public class ChronicleController {

    private final ChronicleService chronicleService;

    public ChronicleController(ChronicleService chronicleService) {
        this.chronicleService = chronicleService;
    }

    @GetMapping("/active")
    public ChronicleYear getActive() {
        return chronicleService.getActive();
    }

    @PostMapping("/create")
    public ChronicleYear create(@RequestBody ChronicleYear chronicleYear) {
        return chronicleService.create(chronicleYear);
    }

    @PostMapping("/edit/{id}")
    public ChronicleYear edit(@RequestBody ChronicleYear dst, @PathVariable("id") ChronicleYear src) {
        return chronicleService.edit(src, dst);
    }

    @GetMapping("/{id}")
    public ChronicleYear get(@PathVariable("id") Long id) {
        return chronicleService.findById(id);
    }


    @GetMapping
    public List<ChronicleYear> list() {
        return this.chronicleService.list();
    }
}
