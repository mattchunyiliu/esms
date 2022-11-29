package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.constants.CacheService;
import kg.kundoluk.school.dto.schedule.*;
import kg.kundoluk.school.endpoint.ScheduleGroupEndpoint;
import kg.kundoluk.school.model.base.BaseEntity;
import kg.kundoluk.school.model.schedule.ScheduleGroup;
import kg.kundoluk.school.repository.SchoolClassRepository;
import kg.kundoluk.school.repository.StudentRepository;
import kg.kundoluk.school.service.schedule.ScheduleGroupService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class ScheduleGroupEndpointImpl implements ScheduleGroupEndpoint {
    private final ScheduleGroupService scheduleGroupService;
    private final SchoolClassRepository schoolClassRepository;
    private final StudentRepository studentRepository;

    public ScheduleGroupEndpointImpl(ScheduleGroupService scheduleGroupService, SchoolClassRepository schoolClassRepository, StudentRepository studentRepository) {
        this.scheduleGroupService = scheduleGroupService;
        this.schoolClassRepository = schoolClassRepository;
        this.studentRepository = studentRepository;
    }

    @CacheEvict(value = CacheService.SCHEDULE_GROUP, key = "#scheduleGroupCreateRequest.schoolId")
    @Override
    public ScheduleGroup create(ScheduleGroupCreateRequest scheduleGroupCreateRequest) {
        ScheduleGroupRequest scheduleGroupRequest = ScheduleGroupRequest.builder()
                .title(scheduleGroupCreateRequest.getTitle())
                .schoolClass(schoolClassRepository.getOne(scheduleGroupCreateRequest.getClassId()))
                .students(studentRepository.findAllByIdIn(scheduleGroupCreateRequest.getStudentList()))
                .build();
        ScheduleGroup scheduleGroup = scheduleGroupService.create(scheduleGroupRequest);
        scheduleGroup.addStudents(studentRepository.findAllByIdIn(scheduleGroupCreateRequest.getStudentList()));

        return scheduleGroupService.save(scheduleGroup);
    }

    @CacheEvict(value = CacheService.SCHEDULE_GROUP, key = "#scheduleGroupUpdateRequest.schoolId")
    @Override
    public ScheduleGroup edit(ScheduleGroupUpdateRequest scheduleGroupUpdateRequest, ScheduleGroup scheduleGroup) {
        ScheduleGroupRequest scheduleGroupRequest = ScheduleGroupRequest.builder()
                .title(scheduleGroupUpdateRequest.getTitle())
                .students(studentRepository.findAllByIdIn(scheduleGroupUpdateRequest.getStudentList()))
                .build();
        return scheduleGroupService.edit(scheduleGroupRequest, scheduleGroup);
    }

    @Override
    public ScheduleGroupSingleResponse findById(Long id) {
        return toScheduleGroupSingleResponse(scheduleGroupService.findById(id));
    }

    @Cacheable(value = CacheService.SCHEDULE_GROUP, key = "#schoolId")
    @Override
    public List<ScheduleGroupResponse> findAllBySchool(Long schoolId) {
        List<ScheduleGroup> scheduleGroups = scheduleGroupService.findAllBySchool(schoolId);
        return scheduleGroups.stream().map(this::toScheduleGroupResponse).collect(Collectors.toList());
    }

    @Override
    public List<ScheduleGroupResponse> findAllByClass(Long classId) {
        List<ScheduleGroup> scheduleGroups = scheduleGroupService.findAllByClass(classId);
        return scheduleGroups.stream().map(this::toScheduleGroupResponse).collect(Collectors.toList());
    }

    @Override
    public List<ScheduleGroupResponse> findAllByStudent(Long studentId) {
        List<ScheduleGroup> scheduleGroups = scheduleGroupService.findAllByStudent(studentId);
        return scheduleGroups.stream().map(this::toScheduleGroupResponse).collect(Collectors.toList());
    }

    @CacheEvict(value = CacheService.SCHEDULE_GROUP, key = "#schoolId")
    @Override
    public void delete(Long id, Long schoolId) {
        scheduleGroupService.delete(id);
    }

    private ScheduleGroupResponse toScheduleGroupResponse(ScheduleGroup scheduleGroup){
        ScheduleGroupResponse scheduleGroupResponse = new ScheduleGroupResponse();
        scheduleGroupResponse.setClassId(scheduleGroup.getSchoolClass().getId());
        scheduleGroupResponse.setClassTitle(scheduleGroup.getSchoolClass().getSelectorTitle());
        scheduleGroupResponse.setGroupId(scheduleGroup.getId());
        scheduleGroupResponse.setGroupTitle(scheduleGroup.getGroupTitle());
        scheduleGroupResponse.setStudentCount(scheduleGroup.getStudents().size());

        return scheduleGroupResponse;
    }

    private ScheduleGroupSingleResponse toScheduleGroupSingleResponse(ScheduleGroup scheduleGroup){
        ScheduleGroupSingleResponse scheduleGroupSingleResponse = new ScheduleGroupSingleResponse();
        scheduleGroupSingleResponse.setTitle(scheduleGroup.getGroupTitle());
        scheduleGroupSingleResponse.setStudentList(scheduleGroup.getStudents().stream().map(BaseEntity::getId).collect(Collectors.toList()));
        return scheduleGroupSingleResponse;
    }
}
