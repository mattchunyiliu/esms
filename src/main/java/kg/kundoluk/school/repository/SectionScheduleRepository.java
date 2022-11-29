package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.SectionScheduleDTO;
import kg.kundoluk.school.model.section.SectionSchedule;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SectionScheduleRepository extends JpaRepository<SectionSchedule, Long> {
    @Query(value = "select id, week_day as weekDay, start_time as startTime, end_time as endTime from section_schedule where section_instructor_id = ?1",nativeQuery = true)
    List<SectionScheduleDTO> findAllBySectionInstructorId(Long id);
}
