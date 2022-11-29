package kg.kundoluk.school.repository;

import kg.kundoluk.school.model.section.SectionInstructor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionInstructorRepository extends JpaRepository<SectionInstructor, Long> {

    @EntityGraph(attributePaths = {"section","students"})
    List<SectionInstructor> findAllByPersonId(Long id);

    @EntityGraph(attributePaths = {"section","students"})
    SectionInstructor findFirstById(Long id);

    @EntityGraph(attributePaths = {"section","person","schedules"})
    List<SectionInstructor> findAllBySectionSchoolId(Long schoolId);

    @EntityGraph(attributePaths = {"section","person","schedules"})
    List<SectionInstructor> findAllBySectionId(Long sectionId);
}
