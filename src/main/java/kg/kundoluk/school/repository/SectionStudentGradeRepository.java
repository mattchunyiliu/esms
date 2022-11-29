package kg.kundoluk.school.repository;

import kg.kundoluk.school.model.section.SectionStudent;
import kg.kundoluk.school.model.section.SectionStudentGrade;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SectionStudentGradeRepository extends JpaRepository<SectionStudentGrade, Long> {

    @EntityGraph(attributePaths = {"sectionStudent.student"})
    @Query("SELECT ssg from SectionStudentGrade  ssg left join ssg.sectionStudent ss left join ss.sectionInstructor si where si.id=?1 and ssg.gradeDate between ?2 and ?3")
    List<SectionStudentGrade> findAllBySectionInstructorId(Long id, LocalDate start, LocalDate end);

    @EntityGraph(attributePaths = {"sectionStudent.student"})
    @Query("SELECT ssg from SectionStudentGrade  ssg left join ssg.sectionStudent ss left join ss.sectionInstructor si left join ss.student s where s.id = ?1 and si.id=?2 and ssg.gradeDate between ?3 and ?4")
    List<SectionStudentGrade> findAllBySectionStudentId(Long studentId, Long sectionInstructorId, LocalDate start, LocalDate end);
}
