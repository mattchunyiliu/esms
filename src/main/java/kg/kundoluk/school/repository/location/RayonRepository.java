package kg.kundoluk.school.repository.location;

import kg.kundoluk.school.model.location.Rayon;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RayonRepository extends JpaRepository<Rayon, Long> {
    @EntityGraph(attributePaths = {"region"})
    List<Rayon> findAll();
}
