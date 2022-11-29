package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.ScheduleClassLoadDTO;
import kg.kundoluk.school.model.schedule.ScheduleClassLoad;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ScheduleClassLoadRepository extends JpaRepository<ScheduleClassLoad,Long> {

    @Modifying
    @Query(value = "UPDATE schedule_class_load SET hour_load = ?2 WHERE id = ?1",nativeQuery = true)
    @Transactional
    void updateHour(Long id, Integer hourLoad);

    @EntityGraph(attributePaths = {"person","schoolCourse.course","schoolClass"})
    List<ScheduleClassLoad> findAllBySchoolClassShiftId(Long shiftId);

    @EntityGraph(attributePaths = {"person","schoolCourse.course","schoolClass"})
    List<ScheduleClassLoad> findAllBySchoolClassSchoolId(Long schoolId);

    @Query(value = "select scl.id, scl.class_id as classId, scl.course_id as courseId, scl.person_id as personId, scl.hour_load as hours from schedule_class_load scl left join school_class sc on scl.class_id = sc.id  where sc.school_id = ?1 order by scl.hour_load desc ", nativeQuery = true)
    List<ScheduleClassLoadDTO> findAllBySchoolInterface(Long schoolId);
}
