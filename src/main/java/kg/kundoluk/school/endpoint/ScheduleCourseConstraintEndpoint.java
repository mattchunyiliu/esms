package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.projection.ScheduleCourseConstraintDTO;
import kg.kundoluk.school.dto.schedule.ScheduleCourseConstraintCreateRequest;

import java.util.List;

public interface ScheduleCourseConstraintEndpoint {
    void create(ScheduleCourseConstraintCreateRequest scheduleCourseConstraintCreateRequest);
    void delete(Long id);
    List<ScheduleCourseConstraintDTO> findAllByShift(Long id);
    List<ScheduleCourseConstraintDTO> findAllBySchool(Long id);
}
