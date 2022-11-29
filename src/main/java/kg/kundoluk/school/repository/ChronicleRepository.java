package kg.kundoluk.school.repository;

import kg.kundoluk.school.model.references.ChronicleYear;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChronicleRepository extends JpaRepository<ChronicleYear, Long> {

    ChronicleYear getByActiveIsTrue();
}
