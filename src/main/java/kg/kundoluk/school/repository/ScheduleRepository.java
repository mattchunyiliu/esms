package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.ScheduleInstructorDTO;
import kg.kundoluk.school.model.enums.WeekDay;
import kg.kundoluk.school.model.schedule.Schedule;
import kg.kundoluk.school.model.school.SchoolClass;
import kg.kundoluk.school.model.school.ShiftTime;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Boolean existsBySchoolClassAndWeekDayAndShiftTime(SchoolClass schoolClass, WeekDay weekDay, ShiftTime shiftTime);

    @EntityGraph(attributePaths = {"person","schoolCourse","schoolCourse.course","schoolClass","shiftTime"})
    List<Schedule> findAllBySchoolClassSchoolId(Long schoolId);

    @EntityGraph(attributePaths = {"person","schoolCourse","schoolCourse.course","schoolClass","shiftTime"})
    List<Schedule> findAllByPersonIdAndWeekDay(Long personId, WeekDay weekDay);

    @EntityGraph(attributePaths = {"person","schoolCourse","schoolCourse.course","schoolClass","shiftTime"})
    List<Schedule> findAllBySchoolClassIdAndWeekDay(Long classId, WeekDay weekDay);

    @EntityGraph(attributePaths = {"person","schoolCourse","schoolCourse.course","schoolClass","shiftTime"})
    List<Schedule> findAllByPersonId(Long personId);

    @EntityGraph(attributePaths = {"person","schoolCourse","schoolCourse.course","schoolClass","shiftTime"})
    List<Schedule> findAllBySchoolClassId(Long classId);

    @Query(value = "select s.id, s.week_day as weekDay, st.id as shiftTimeId, st.title as shiftTimeTitle from schedule s left join shift_time st on st.id = s.shift_time_id where (?1 = 0 or s.person_id = ?1) and s.class_id = ?2 and (?3 = 0 or s.course_id = ?3)", nativeQuery = true)
    List<ScheduleInstructorDTO> findAllByPersonClassCourse(Long instructorId, Long classId, Long courseId);

    @EntityGraph(attributePaths = {"person","schoolCourse","schoolCourse.course","schoolClass","shiftTime","scheduleGroup"})
    Schedule findFirstById(Long id);

    @Modifying
    @Query(value = "DELETE FROM schedule WHERE id IN (select s.id from " +
            "schedule s " +
            "left join shift_time st on s.shift_time_id = st.id " +
            "left join shift s2 on st.shift_id = s2.id where s2.school_id = ?1 and s2.id = ?2)",nativeQuery = true)
    @Transactional
    void massDelete(Long schoolId, Long shiftId);
}
