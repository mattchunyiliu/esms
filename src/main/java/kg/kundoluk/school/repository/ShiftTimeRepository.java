package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.ShiftTimeViewDTO;
import kg.kundoluk.school.model.school.ShiftTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShiftTimeRepository extends JpaRepository<ShiftTime, Long> {

    @Query(value = "select * from shift_time where shift_id = ?1", nativeQuery = true)
    List<ShiftTime> findAllByShiftId(Long shiftId);

    @Query("SELECT sft FROM ShiftTime sft LEFT JOIN FETCH sft.shift st LEFT JOIN st.school sch WHERE sch.id = ?1")
    List<ShiftTime> findAllBySchoolId(Long schoolId);

    @Query(value = "select id, title as name from shift_time where shift_id = ?1", nativeQuery = true)
    List<ShiftTimeViewDTO> findAllByShiftInterface(Long shiftId);
}
