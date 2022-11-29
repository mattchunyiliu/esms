package kg.kundoluk.school.repository;

import kg.kundoluk.school.model.section.Section;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long> {
    @EntityGraph(attributePaths = {"sectionInstructors","sectionInstructors.person","sectionInstructors.students"})
    List<Section> findAllBySchoolId(Long schoolId);
}
