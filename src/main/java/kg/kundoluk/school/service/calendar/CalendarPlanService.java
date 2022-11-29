package kg.kundoluk.school.service.calendar;

import kg.kundoluk.school.dto.calendar.CalendarPlanRequest;
import kg.kundoluk.school.exception.ConstraintMappingException;
import kg.kundoluk.school.model.instructor.CalendarPlan;

import java.util.List;

public interface CalendarPlanService {
    CalendarPlan create(CalendarPlanRequest calendarPlanRequest);
    CalendarPlan edit(CalendarPlanRequest calendarPlanRequest, CalendarPlan calendarPlan);
    List<CalendarPlan> findAllByInstructorChronicle(Long instructorId, Long chronicleId);
    CalendarPlan findById(Long id);
    CalendarPlan findByInstructorClassCourseChronicle(Long instructorId, Long classId, Long courseId, Long chronicleId);
    void delete(Long id) throws ConstraintMappingException;
}
