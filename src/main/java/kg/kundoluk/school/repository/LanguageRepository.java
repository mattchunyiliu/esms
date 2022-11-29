package kg.kundoluk.school.repository;

import kg.kundoluk.school.model.references.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    Language getByCode(String code);
}
