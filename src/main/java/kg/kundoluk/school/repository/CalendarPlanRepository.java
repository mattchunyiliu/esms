package kg.kundoluk.school.repository;

import kg.kundoluk.school.model.instructor.CalendarPlan;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendarPlanRepository extends JpaRepository<CalendarPlan, Long> {
    @EntityGraph(attributePaths = {"schoolCourse.course","schoolClass","topicSet","schoolClass.school"})
    List<CalendarPlan> findAllByPersonIdAndChronicleYearIdAndIsDefaultFalseOrderByCreatedAtDesc(Long instructorId, Long chronicleId);

    @EntityGraph(attributePaths = {"schoolCourse.course","schoolClass","topicSet"})
    CalendarPlan findFirstById(Long id);

    CalendarPlan findFirstByPersonIdAndSchoolClassIdAndSchoolCourseIdAndChronicleYearId(Long instructorId, Long classId, Long courseId, Long chronicleId);
}
