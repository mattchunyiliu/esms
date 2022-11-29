package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.SchoolPresetDTO;
import kg.kundoluk.school.model.school.SchoolPreset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SchoolPresetRepository extends JpaRepository<SchoolPreset, Long> {
    SchoolPreset findFirstBySchoolIdAndChronicleYearId(Long schoolId, Long chronicleId);

    @Query(value = "select sp.id, sp.step_number as stepNumber, sp.is_preset as preset, sp.is_class_raised as isClassRaised from school_preset sp where sp.school_id = ?1 and sp.chronicle_id = ?2 limit 1", nativeQuery = true)
    SchoolPresetDTO findBySchoolChronicle(Long schoolId, Long chronicleId);

    @Query(value = "select sp.id, sp.step_number as stepNumber, sp.is_preset as preset, s.id as schoolId, s.school_name as schoolName from school s left outer join school_preset sp on s.id = sp.school_id where (sp.chronicle_id is null or sp.chronicle_id = ?1)", nativeQuery = true)
    List<SchoolPresetDTO> findAllByChronicle(Long chronicleId);
}
