package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.GradeDTO;
import kg.kundoluk.school.dto.projection.GradeStudentAnalytic;
import kg.kundoluk.school.model.grade.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    List<Grade> findAllByIdIn(List<Long> ids);

    @Query(value = "select g.id as gradeId, g.shift_time_id as shiftTimeId, g.grade_date as gradeDate, sc.id as studentCourseId, g.mark as mark, g.grade_type as gradeMarkType, g.topic_id as topicId from grade g left join student_course sc on g.m2m_student_course_id = sc.id where (?1 = 0 or g.instructor_id = ?1) and (?2 = 0 or sc.course_id = ?2) and sc.class_id = ?3 and g.grade_date between ?4 and ?5",nativeQuery = true)
    List<GradeDTO> findAllByPersonCourseClassDateRange(Long personId, Long courseId, Long classId, LocalDate start, LocalDate end);

    @Query(value = "select g.id as gradeId, g.shift_time_id as shiftTimeId, g.grade_date as gradeDate, sc.id as studentCourseId, g.mark as mark, g.grade_type as gradeMarkType, g.topic_id as topicId from grade g left join student_course sc on g.m2m_student_course_id = sc.id where  sc.student_id = ?1 and (?2 = 0 or sc.course_id = ?2) and g.grade_date between ?3 and ?4",nativeQuery = true)
    List<GradeDTO> findAllByStudentCourseDateRange(Long studentId, Long courseId, LocalDate start, LocalDate end);

    @Query(value = "select g.id as gradeId, g.shift_time_id as shiftTimeId, g.grade_date as gradeDate, sc.id as studentCourseId, g.mark as mark, g.grade_type as gradeMarkType, g.topic_id as topicId from grade g left join student_course sc on g.m2m_student_course_id = sc.id where  g.topic_id = ?1",nativeQuery = true)
    List<GradeDTO> findAllByCalendarTopicId(Long topicId);

    @Query(value = "select g.id as gradeId, g.shift_time_id as shiftTimeId, g.grade_date as gradeDate, sc.id as studentCourseId, g.mark as mark, g.grade_type as gradeMarkType, g.topic_id as topicId from grade g left join student_course sc on g.m2m_student_course_id = sc.id where  g.shift_time_id = ?1",nativeQuery = true)
    List<GradeDTO> findAllByShiftTimeId(Long shiftTimeId);

    @Modifying
    @Query(value = "delete from grade where topic_id = ?1",nativeQuery = true)
    @Transactional
    void deleteAllByTopic(Long topicId);
}
