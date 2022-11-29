package kg.kundoluk.school.repository.location;

import kg.kundoluk.school.model.location.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
