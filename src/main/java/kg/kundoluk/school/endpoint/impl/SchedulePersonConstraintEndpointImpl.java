package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.projection.SchedulePersonConstraintDTO;
import kg.kundoluk.school.dto.schedule.SchedulePersonConstraintCreateRequest;
import kg.kundoluk.school.dto.schedule.SchedulePersonConstraintRequest;
import kg.kundoluk.school.endpoint.SchedulePersonConstraintEndpoint;
import kg.kundoluk.school.repository.PersonRepository;
import kg.kundoluk.school.repository.ShiftTimeRepository;
import kg.kundoluk.school.service.schedule.SchedulePersonConstraintService;

import java.util.List;

@Endpoint
public class SchedulePersonConstraintEndpointImpl implements SchedulePersonConstraintEndpoint {
    private final SchedulePersonConstraintService schedulePersonConstraintService;
    private final PersonRepository personRepository;
    private final ShiftTimeRepository shiftTimeRepository;

    public SchedulePersonConstraintEndpointImpl(SchedulePersonConstraintService schedulePersonConstraintService, PersonRepository personRepository, ShiftTimeRepository shiftTimeRepository) {
        this.schedulePersonConstraintService = schedulePersonConstraintService;
        this.personRepository = personRepository;
        this.shiftTimeRepository = shiftTimeRepository;
    }

    @Override
    public void create(SchedulePersonConstraintCreateRequest schedulePersonConstraintCreateRequest) {
        SchedulePersonConstraintRequest schedulePersonConstraintRequest = SchedulePersonConstraintRequest.builder()
                .person(personRepository.getOne(schedulePersonConstraintCreateRequest.getPersonId()))
                .shiftTime(shiftTimeRepository.getOne(schedulePersonConstraintCreateRequest.getShiftTimeId()))
                .weekDay(schedulePersonConstraintCreateRequest.getWeekDay())
                .build();
        schedulePersonConstraintService.create(schedulePersonConstraintRequest);
    }

    @Override
    public void delete(Long id) {
        schedulePersonConstraintService.delete(id);
    }

    @Override
    public List<SchedulePersonConstraintDTO> findAllByShift(Long shiftId) {
        return schedulePersonConstraintService.findAllByShift(shiftId);
    }

    @Override
    public List<SchedulePersonConstraintDTO> findAllBySchool(Long schoolId) {
        return schedulePersonConstraintService.findAllBySchool(schoolId);
    }
}
