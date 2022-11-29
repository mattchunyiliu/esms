package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.CalendarTopicDTO;
import kg.kundoluk.school.model.instructor.CalendarTopic;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CalendarTopicRepository extends JpaRepository<CalendarTopic, Long> {

    @EntityGraph(attributePaths = {"quarter"})
    List<CalendarTopic> findAllByCalendarPlanId(Long id);

    @Query(value = "select ct.id, ct.title, ct.quarter_id as quarterId, ct.topic_result as topicResult, ct.topic_visibility as topicVisibility, ct.date_plan as topicDate, ct.hours as topicHour, ct.is_passed as isPassed from calendar_topic ct left join calendar_plan cp on cp.id = ct.calendar_plan_id  where cp.person_id = ?1 and cp.class_id = ?2 and cp.course_id = ?3 and cp.chronicle_id = ?4",nativeQuery = true)
    List<CalendarTopicDTO> findAllByInstructorClassCourse(Long instructorId, Long classId, Long courseId, Long chronicleId);
}
