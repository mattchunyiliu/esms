package kg.kundoluk.school.repository;

import kg.kundoluk.school.model.instructor.Assignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    @EntityGraph(attributePaths = {"attachments","calendarTopic.calendarPlan.schoolClass", "calendarTopic.calendarPlan.schoolCourse.course"})
    @Query("select a from Assignment a left join a.calendarTopic ct left join ct.calendarPlan cp left join cp.chronicleYear cy left join cp.person p where p.id = ?1 and cy.id = ?2")
    List<Assignment> findAllByInstructor(Long instructorId, Long chronicleId);

    @EntityGraph(attributePaths = {"attachments","calendarTopic.calendarPlan.schoolClass", "calendarTopic.calendarPlan.schoolCourse.course"})
    @Query("select a from Assignment a left join a.calendarTopic ct left join ct.calendarPlan cp left join cp.chronicleYear cy left join cp.person p left join cp.schoolClass scl left join cp.schoolCourse sc where p.id=?1 and scl.id=?2 and sc.id=?3 and cy.id=?4")
    Page<Assignment> findAllByCalendarTopic_CalendarPlan_PersonId(Long instructorId, Long classId, Long courseId, Long chronicleId, Pageable pageable);

    @EntityGraph(attributePaths = {"attachments","calendarTopic.calendarPlan.schoolClass", "calendarTopic.calendarPlan.schoolCourse.course"})
    @Query("select a from Assignment a left join a.calendarTopic ct left join ct.calendarPlan cp left join cp.chronicleYear cy left join cp.schoolClass scl where scl.id=?1 and cy.id=?2")
    Page<Assignment> findAllByClassId(Long classId, Long chronicleId, Pageable pageable);

    @EntityGraph(attributePaths = {"attachments","calendarTopic.calendarPlan.schoolClass", "calendarTopic.calendarPlan.schoolCourse.course"})
    Assignment findFirstById(Long id);
}
