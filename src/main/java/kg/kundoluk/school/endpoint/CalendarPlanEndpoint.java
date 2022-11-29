package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.calendar.CalendarPlanCloneCreateRequest;
import kg.kundoluk.school.dto.calendar.CalendarPlanCreateRequest;
import kg.kundoluk.school.dto.calendar.CalendarPlanResponse;
import kg.kundoluk.school.dto.calendar.CalendarPlanUpdateRequest;
import kg.kundoluk.school.exception.ConstraintMappingException;
import kg.kundoluk.school.model.instructor.CalendarPlan;

import java.util.List;

public interface CalendarPlanEndpoint {
    CalendarPlan create(CalendarPlanCreateRequest calendarPlanCreateRequest);
    CalendarPlan edit(String title, CalendarPlan calendarPlan);
    CalendarPlan edit(CalendarPlanUpdateRequest calendarPlanUpdateRequest, CalendarPlan calendarPlan);
    CalendarPlanResponse getById(Long id);
    List<CalendarPlanResponse> findAllByInstructorChronicle(Long instructorId, Long chronicleId);
    Integer clone(CalendarPlanCloneCreateRequest calendarPlanCloneCreateRequest);
    void delete(Long id) throws ConstraintMappingException;
}
