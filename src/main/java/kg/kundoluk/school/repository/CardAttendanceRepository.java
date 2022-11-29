package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.AnalyticSchoolCount;
import kg.kundoluk.school.model.student.CardAttendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CardAttendanceRepository extends JpaRepository<CardAttendance,Long> {

    @EntityGraph(attributePaths = {"student"})
    Page<CardAttendance> findAllByStudentId(Long studentId, Pageable pageable);

    @Query(value = "select count(distinct s.id) as totalCount, s2.id as schoolId, s2.school_name as schoolName\n" +
            "from card_attendance ca\n" +
            "    left join student s on s.id = ca.student_id\n" +
            "    left join user_school us on s.user_id = us.user_id\n" +
            "    left join school s2 on us.school_id = s2.id\n" +
            "where ca.attendance_date between ?1 and ?2\n" +
            "group by s2.id", nativeQuery = true)
    List<AnalyticSchoolCount> getCardAttendanceBySchool(LocalDate start, LocalDate end);
}
