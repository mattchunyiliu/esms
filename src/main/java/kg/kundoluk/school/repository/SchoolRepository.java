package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.SchoolDTO;
import kg.kundoluk.school.model.school.School;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface SchoolRepository extends JpaRepository<School, Long> {

    @Query(value = "select id, school_name as name, study_day as studyDay, address, phone_number as phone, organization_type as organizationType from school where id = ?1", nativeQuery = true)
    SchoolDTO getSchoolInterface(Long id);

    @EntityGraph(attributePaths = {"rayon","rayon.region","town"})
    List<School> findAllByIsTestOrderByNameAsc(Boolean isTest);

    @Query(value = "select id, school_name as name, study_day as studyDay, address, phone_number as phone, organization_type as organizationType from school where rayon_id = ?1", nativeQuery = true)
    List<SchoolDTO> getByRayonId(Long id);

    @Query(value = "select id, school_name as name, study_day as studyDay, address, phone_number as phone, organization_type as organizationType from school where town_id = ?1", nativeQuery = true)
    List<SchoolDTO> getByTownId(Long id);
}
