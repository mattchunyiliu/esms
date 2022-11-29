package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.ScheduleCourseConstraintDTO;
import kg.kundoluk.school.model.schedule.ScheduleCourseConstraint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleCourseConstraintRepository extends JpaRepository<ScheduleCourseConstraint, Long> {

    @Query(value = "select scc.id, scc.course_id as courseId, scc.shift_time_id as shiftTimeId, scc.week_day as weekDay, st.title as shiftTimeName from schedule_course_constraint scc left join shift_time st on scc.shift_time_id = st.id where st.shift_id=?1",nativeQuery = true)
    List<ScheduleCourseConstraintDTO> findAllByShift(Long id);

    @Query(value = "select scc.id, scc.course_id as courseId, scc.shift_time_id as shiftTimeId, scc.week_day as weekDay, st.title as shiftTimeName from schedule_course_constraint scc left join shift_time st on scc.shift_time_id = st.id left join shift s on st.shift_id = s.id where s.school_id=?1",nativeQuery = true)
    List<ScheduleCourseConstraintDTO> findAllBySchool(Long id);
}
