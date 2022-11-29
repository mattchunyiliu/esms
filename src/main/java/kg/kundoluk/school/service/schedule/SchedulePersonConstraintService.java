package kg.kundoluk.school.service.schedule;

import kg.kundoluk.school.dto.projection.SchedulePersonConstraintDTO;
import kg.kundoluk.school.dto.schedule.SchedulePersonConstraintRequest;
import kg.kundoluk.school.model.schedule.SchedulePersonConstraint;

import java.util.List;

public interface SchedulePersonConstraintService {
    SchedulePersonConstraint create(SchedulePersonConstraintRequest schedulePersonConstraintRequest);
    void delete(Long id);
    List<SchedulePersonConstraintDTO> findAllByShift(Long shiftId);
    List<SchedulePersonConstraintDTO> findAllBySchool(Long schoolId);
}
