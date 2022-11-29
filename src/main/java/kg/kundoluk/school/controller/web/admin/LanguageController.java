package kg.kundoluk.school.controller.web.admin;

import kg.kundoluk.school.model.references.Language;
import kg.kundoluk.school.service.references.LanguageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/web/v1/language")
public class LanguageController {

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    public List<Language> list() {
        return this.languageService.list();
    }
}
