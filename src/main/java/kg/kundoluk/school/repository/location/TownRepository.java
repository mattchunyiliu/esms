package kg.kundoluk.school.repository.location;

import kg.kundoluk.school.model.location.Town;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TownRepository extends JpaRepository<Town, Long> {

    @EntityGraph(attributePaths = {"rayon.region"})
    List<Town> findAllByRayonId(Long rayonId);

    @EntityGraph(attributePaths = {"rayon.region"})
    List<Town> findAll();
}
