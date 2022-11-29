package kg.kundoluk.school.repository;

import kg.kundoluk.school.model.school.Quarter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuarterRepository extends JpaRepository<Quarter, Long> {
    @Query(value = "SELECT * FROM quarter WHERE school_id = ?1 and chronicle_id = ?2", nativeQuery = true)
    List<Quarter> findAllBySchoolIdAndChronicleId(Long schoolId, Long chronicleId);
}
