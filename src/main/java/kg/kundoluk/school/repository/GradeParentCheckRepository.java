package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.GradeParentCheckResponse;
import kg.kundoluk.school.model.grade.GradeParentCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface GradeParentCheckRepository extends JpaRepository<GradeParentCheck, Long> {
    @Query(value = "select s.id as studentId, concat(s.last_name,' ',s.first_name) as studentTitle, gpc.is_checked as checked\n" +
            "from student s left join grade_parent_check gpc on gpc.student_id = s.id\n" +
            "where (?1 = 0 or s.id = ?1) and s.class_id = ?2 and (gpc.start_date is null or gpc.start_date = ?3) and (gpc.end_date is null  or gpc.end_date = ?4)", nativeQuery = true)
    List<GradeParentCheckResponse> getList(Long studentId, Long classId, LocalDate startDate, LocalDate endDate);
}
