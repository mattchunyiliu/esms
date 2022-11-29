package kg.kundoluk.school.repository;

import kg.kundoluk.school.model.school.School;
import kg.kundoluk.school.model.school.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, Long> {

    @Query(value = "select * from shift where school_id = ?1", nativeQuery = true)
    List<Shift> findAllBySchoolId(Long schoolId);

    Boolean existsAllBySchoolAndTitle(School school, String title);
}
