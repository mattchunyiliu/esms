package kg.kundoluk.school.repository;

import kg.kundoluk.school.model.schedule.ScheduleGroup;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleGroupRepository extends JpaRepository<ScheduleGroup, Long> {

    @EntityGraph(attributePaths = {"schoolClass","students"})
    List<ScheduleGroup> findAllBySchoolClassSchoolId(Long schoolId);

    @EntityGraph(attributePaths = {"schoolClass","students"})
    List<ScheduleGroup> findAllBySchoolClassId(Long classId);

    @EntityGraph(attributePaths = {"schoolClass","students"})
    @Query("SELECT sc FROM ScheduleGroup  sc left join sc.students s where s.id = ?1")
    List<ScheduleGroup> findAllByStudentId(Long studentId);

    @EntityGraph(attributePaths = {"schoolClass","students"})
    ScheduleGroup findFirstById(Long id);
}
