package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.calendar.CalendarTopicCreateRequest;
import kg.kundoluk.school.dto.calendar.CalendarTopicResponse;
import kg.kundoluk.school.dto.calendar.CalendarTopicUpdateRequest;
import kg.kundoluk.school.exception.ConstraintMappingException;
import kg.kundoluk.school.model.instructor.CalendarTopic;

import java.util.List;

public interface CalendarTopicEndpoint {
    CalendarTopic create(CalendarTopicCreateRequest calendarTopicCreateRequest);
    CalendarTopic edit(CalendarTopicUpdateRequest calendarTopicUpdateRequest, CalendarTopic calendarTopic);
    List<CalendarTopicResponse> findAllByCalendarPlan(Long calendarPlanId);
    List<CalendarTopicResponse> findAllByInstructorClassCourse(Long instructorId, Long classId, Long courseId, Long chronicleId);
    void delete(Long id) throws ConstraintMappingException;
    CalendarTopic findById(Long id);
}
