package kg.kundoluk.school.repository;

import kg.kundoluk.school.model.section.SectionStudent;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionStudentRepository extends JpaRepository<SectionStudent, Long> {

    @EntityGraph(attributePaths = {"sectionInstructor","sectionInstructor.section","sectionInstructor.person"})
    List<SectionStudent> findAllByStudentIdAndChronicleYearId(Long studentId, Long chronicleId);

    @EntityGraph(attributePaths = {"student","student.user","student.schoolClass"})
    List<SectionStudent> findAllBySectionInstructorId(Long id);

    SectionStudent findSectionStudentBySectionInstructorIdAndStudentIdAndChronicleYearId(Long sectionId, Long studentId, Long chronicleId);
}
