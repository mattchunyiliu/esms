package kg.kundoluk.school.service.schedule.impl;

import kg.kundoluk.school.dto.projection.SchedulePersonConstraintDTO;
import kg.kundoluk.school.dto.schedule.SchedulePersonConstraintRequest;
import kg.kundoluk.school.model.schedule.SchedulePersonConstraint;
import kg.kundoluk.school.repository.SchedulePersonConstraintRepository;
import kg.kundoluk.school.service.schedule.SchedulePersonConstraintService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SchedulePersonConstraintServiceImpl implements SchedulePersonConstraintService {
    private final SchedulePersonConstraintRepository schedulePersonConstraintRepository;

    public SchedulePersonConstraintServiceImpl(SchedulePersonConstraintRepository schedulePersonConstraintRepository) {
        this.schedulePersonConstraintRepository = schedulePersonConstraintRepository;
    }

    @Override
    public SchedulePersonConstraint create(SchedulePersonConstraintRequest schedulePersonConstraintRequest) {
        return schedulePersonConstraintRepository.save(SchedulePersonConstraint
                .builder()
                .isAllowed(false)
                .person(schedulePersonConstraintRequest.getPerson())
                .shiftTime(schedulePersonConstraintRequest.getShiftTime())
                .weekDay(schedulePersonConstraintRequest.getWeekDay())
                .build());
    }

    @Override
    public void delete(Long id) {
        schedulePersonConstraintRepository.deleteById(id);
    }

    @Override
    public List<SchedulePersonConstraintDTO> findAllByShift(Long shiftId) {
        return schedulePersonConstraintRepository.findAllByShift(shiftId);
    }

    @Override
    public List<SchedulePersonConstraintDTO> findAllBySchool(Long schoolId) {
        return schedulePersonConstraintRepository.findAllBySchool(schoolId);
    }
}
