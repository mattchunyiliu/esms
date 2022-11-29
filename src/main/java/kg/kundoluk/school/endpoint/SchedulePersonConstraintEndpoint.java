package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.projection.SchedulePersonConstraintDTO;
import kg.kundoluk.school.dto.schedule.SchedulePersonConstraintCreateRequest;

import java.util.List;

public interface SchedulePersonConstraintEndpoint {
    void create(SchedulePersonConstraintCreateRequest schedulePersonConstraintCreateRequest);
    void delete(Long id);
    List<SchedulePersonConstraintDTO> findAllByShift(Long shiftId);
    List<SchedulePersonConstraintDTO> findAllBySchool(Long schoolId);
}
