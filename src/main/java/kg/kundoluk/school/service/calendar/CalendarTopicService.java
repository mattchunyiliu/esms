package kg.kundoluk.school.service.calendar;

import kg.kundoluk.school.dto.calendar.CalendarTopicRequest;
import kg.kundoluk.school.dto.projection.CalendarTopicDTO;
import kg.kundoluk.school.exception.ConstraintMappingException;
import kg.kundoluk.school.model.instructor.CalendarTopic;

import java.util.List;

public interface CalendarTopicService {
    CalendarTopic create(CalendarTopicRequest calendarTopicRequest);
    CalendarTopic edit(CalendarTopicRequest calendarTopicRequest, CalendarTopic calendarTopic);
    List<CalendarTopic> findAllByCalendarPlan(Long id);
    List<CalendarTopicDTO> findAllByInstructorClassCourse(Long instructorId, Long classId, Long courseId, Long chronicleId);
    void delete(Long id) throws ConstraintMappingException;
    CalendarTopic findById(Long id);
}
