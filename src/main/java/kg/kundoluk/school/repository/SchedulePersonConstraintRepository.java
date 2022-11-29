package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.SchedulePersonConstraintDTO;
import kg.kundoluk.school.model.schedule.SchedulePersonConstraint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SchedulePersonConstraintRepository extends JpaRepository<SchedulePersonConstraint, Long> {


    @Query(value = "select spc.id, spc.person_id as personId, spc.shift_time_id as shiftTimeId, spc.week_day as weekDay, st.title as shiftTimeName from schedule_person_constraint spc left join shift_time st on spc.shift_time_id = st.id where st.shift_id = ?1",nativeQuery = true)
    List<SchedulePersonConstraintDTO> findAllByShift(Long shiftId);

    @Query(value = "select spc.id, spc.person_id as personId, spc.shift_time_id as shiftTimeId, spc.week_day as weekDay, st.title as shiftTimeName from schedule_person_constraint spc left join shift_time st on spc.shift_time_id = st.id left join shift s on st.shift_id = s.id where s.school_id = ?1",nativeQuery = true)
    List<SchedulePersonConstraintDTO> findAllBySchool(Long schoolId);
}
