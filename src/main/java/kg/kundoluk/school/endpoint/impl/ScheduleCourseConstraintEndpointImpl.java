package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.projection.ScheduleCourseConstraintDTO;
import kg.kundoluk.school.dto.schedule.ScheduleCourseConstraintCreateRequest;
import kg.kundoluk.school.dto.schedule.ScheduleCourseConstraintRequest;
import kg.kundoluk.school.endpoint.ScheduleCourseConstraintEndpoint;
import kg.kundoluk.school.model.schedule.ScheduleCourseConstraint;
import kg.kundoluk.school.repository.SchoolCourseRepository;
import kg.kundoluk.school.repository.ShiftTimeRepository;
import kg.kundoluk.school.service.schedule.ScheduleCourseConstraintService;

import java.util.List;

@Endpoint
public class ScheduleCourseConstraintEndpointImpl implements ScheduleCourseConstraintEndpoint {
    private final ScheduleCourseConstraintService scheduleCourseConstraintService;
    private final SchoolCourseRepository schoolCourseRepository;
    private final ShiftTimeRepository shiftTimeRepository;

    public ScheduleCourseConstraintEndpointImpl(ScheduleCourseConstraintService scheduleCourseConstraintService, SchoolCourseRepository schoolCourseRepository, ShiftTimeRepository shiftTimeRepository) {
        this.scheduleCourseConstraintService = scheduleCourseConstraintService;
        this.schoolCourseRepository = schoolCourseRepository;
        this.shiftTimeRepository = shiftTimeRepository;
    }

    @Override
    public void create(ScheduleCourseConstraintCreateRequest scheduleCourseConstraintCreateRequest) {
        ScheduleCourseConstraintRequest scheduleCourseConstraintRequest = ScheduleCourseConstraintRequest.builder()
                .schoolCourse(schoolCourseRepository.getOne(scheduleCourseConstraintCreateRequest.getCourseId()))
                .shiftTime(shiftTimeRepository.getOne(scheduleCourseConstraintCreateRequest.getShiftTimeId()))
                .weekDay(scheduleCourseConstraintCreateRequest.getWeekDay())
                .build();
        scheduleCourseConstraintService.create(scheduleCourseConstraintRequest);
    }

    @Override
    public void delete(Long id) {
        scheduleCourseConstraintService.delete(id);
    }

    @Override
    public List<ScheduleCourseConstraintDTO> findAllByShift(Long id) {
        return scheduleCourseConstraintService.findAllByShift(id);
    }

    @Override
    public List<ScheduleCourseConstraintDTO> findAllBySchool(Long id) {
        return scheduleCourseConstraintService.findAllBySchool(id);
    }
}
