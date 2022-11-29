package kg.kundoluk.school.service.schedule;

import kg.kundoluk.school.dto.projection.ScheduleCourseConstraintDTO;
import kg.kundoluk.school.dto.schedule.ScheduleCourseConstraintRequest;
import kg.kundoluk.school.model.schedule.ScheduleCourseConstraint;

import java.util.List;

public interface ScheduleCourseConstraintService {
    ScheduleCourseConstraint create(ScheduleCourseConstraintRequest scheduleCourseConstraintRequest);
    void delete(Long id);
    List<ScheduleCourseConstraintDTO> findAllByShift(Long id);
    List<ScheduleCourseConstraintDTO> findAllBySchool(Long id);
}
