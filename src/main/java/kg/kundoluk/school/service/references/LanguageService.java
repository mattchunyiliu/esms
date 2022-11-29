package kg.kundoluk.school.service.references;

import kg.kundoluk.school.constants.CacheService;
import kg.kundoluk.school.model.references.Language;
import kg.kundoluk.school.repository.LanguageRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Cacheable(value = CacheService.LANGUAGE)
    public List<Language> list() {
        return languageRepository.findAll();
    }
}
