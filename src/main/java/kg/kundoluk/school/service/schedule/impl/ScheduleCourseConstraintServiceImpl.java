package kg.kundoluk.school.service.schedule.impl;

import kg.kundoluk.school.dto.projection.ScheduleCourseConstraintDTO;
import kg.kundoluk.school.dto.schedule.ScheduleCourseConstraintRequest;
import kg.kundoluk.school.model.schedule.ScheduleCourseConstraint;
import kg.kundoluk.school.repository.ScheduleCourseConstraintRepository;
import kg.kundoluk.school.service.schedule.ScheduleCourseConstraintService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ScheduleCourseConstraintServiceImpl implements ScheduleCourseConstraintService {
    private final ScheduleCourseConstraintRepository scheduleCourseConstraintRepository;

    public ScheduleCourseConstraintServiceImpl(ScheduleCourseConstraintRepository scheduleCourseConstraintRepository) {
        this.scheduleCourseConstraintRepository = scheduleCourseConstraintRepository;
    }

    @Override
    public ScheduleCourseConstraint create(ScheduleCourseConstraintRequest scheduleCourseConstraintRequest) {
        return scheduleCourseConstraintRepository.save(ScheduleCourseConstraint.builder()
                .weekDay(scheduleCourseConstraintRequest.getWeekDay())
                .isAllowed(false)
                .schoolCourse(scheduleCourseConstraintRequest.getSchoolCourse())
                .shiftTime(scheduleCourseConstraintRequest.getShiftTime())
                .build());
    }

    @Override
    public void delete(Long id) {
        scheduleCourseConstraintRepository.deleteById(id);
    }

    @Override
    public List<ScheduleCourseConstraintDTO> findAllByShift(Long id) {
        return scheduleCourseConstraintRepository.findAllByShift(id);
    }

    @Override
    public List<ScheduleCourseConstraintDTO> findAllBySchool(Long id) {
        return scheduleCourseConstraintRepository.findAllBySchool(id);
    }
}
