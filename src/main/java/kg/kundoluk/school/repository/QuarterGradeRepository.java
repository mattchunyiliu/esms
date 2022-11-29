package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.QuarterGradeDTO;
import kg.kundoluk.school.model.grade.QuarterGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuarterGradeRepository extends JpaRepository<QuarterGrade, Long> {
    Boolean deleteAllByIdIn(List<Long> ids);

    @Query(value = "select qg.id as gradeId, sc.id as studentCourseId, q.id as quarterId, qg.mark as mark, qg.grade_type as gradeMarkType from quarter_grade qg left join student_course sc on qg.m2m_student_course_id = sc.id left join quarter q on qg.quarter_id = q.id where qg.instructor_id=?1 and sc.course_id=?2 and qg.class_id=?3 and q.chronicle_id=?4", nativeQuery = true)
    List<QuarterGradeDTO> getAllByPerson(Long personId, Long courseId, Long classId, Long chronicleId);

    @Query(value = "select qg.id as gradeId, sc.id as studentCourseId, q.id as quarterId, qg.mark as mark, qg.grade_type as gradeMarkType from quarter_grade qg left join student_course sc on qg.m2m_student_course_id = sc.id left join quarter q on qg.quarter_id = q.id where sc.student_id=?1 and q.chronicle_id=?2", nativeQuery = true)
    List<QuarterGradeDTO> getAllByStudent(Long studentId, Long chronicleId);
}
