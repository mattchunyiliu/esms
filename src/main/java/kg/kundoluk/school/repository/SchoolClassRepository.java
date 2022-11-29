package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.SchoolClassBaseDTO;
import kg.kundoluk.school.dto.projection.SchoolClassDTO;
import kg.kundoluk.school.model.school.SchoolClass;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {

    @Query(value = "SELECT cl.id, cl.label as classLabel, cl.level as classLevel, cl.language_id as languageId, cl.shift_id as shiftId FROM school_class as cl WHERE cl.school_id = ?1", nativeQuery = true)
    List<SchoolClassDTO> findBySchoolSQL(Long schoolId);

    @EntityGraph(attributePaths = {"language","shift","person"})
    List<SchoolClass> findAllBySchoolIdOrderByLevelDesc(Long schoolId);

    @Query(value = "SELECT cl.id, cl.label as classLabel, cl.level as classLevel, cl.school_id as schoolId FROM school_class as cl WHERE cl.person_id = ?1", nativeQuery = true)
    List<SchoolClassBaseDTO> findByPersonSQL(Long personId);

    @Query(value = "SELECT cl.id, cl.label as classLabel, cl.level as classLevel, cl.language_id as languageId, cl.shift_id as shiftId, cl.school_id as schoolId, cl.person_id as personId, cl.class_type as classType, concat(p.last_name,' ',p.first_name) as personTitle FROM school_class as cl LEFT JOIN person p on cl.person_id = p.id WHERE cl.id = ?1 limit 1", nativeQuery = true)
    SchoolClassDTO findByIdProjection(Long id);
}
