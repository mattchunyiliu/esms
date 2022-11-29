package kg.kundoluk.school.service.schedule.impl;

import kg.kundoluk.school.dto.schedule.ScheduleGroupRequest;
import kg.kundoluk.school.model.schedule.ScheduleGroup;
import kg.kundoluk.school.repository.ScheduleGroupRepository;
import kg.kundoluk.school.service.schedule.ScheduleGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ScheduleGroupServiceImpl implements ScheduleGroupService {
    private final ScheduleGroupRepository scheduleGroupRepository;

    public ScheduleGroupServiceImpl(ScheduleGroupRepository scheduleGroupRepository) {
        this.scheduleGroupRepository = scheduleGroupRepository;
    }

    @Override
    public ScheduleGroup create(ScheduleGroupRequest scheduleGroupRequest) {
        ScheduleGroup scheduleGroup = new ScheduleGroup()
                .setGroupTitle(scheduleGroupRequest.getTitle())
                .setSchoolClass(scheduleGroupRequest.getSchoolClass());
        return scheduleGroupRepository.save(scheduleGroup);
    }

    @Override
    public ScheduleGroup edit(ScheduleGroupRequest scheduleGroupRequest, ScheduleGroup scheduleGroup) {
        return scheduleGroupRepository.save(
                scheduleGroup
                        .setGroupTitle(scheduleGroupRequest.getTitle())
                        .setStudents(scheduleGroupRequest.getStudents())
        );
    }

    @Override
    public ScheduleGroup save(ScheduleGroup scheduleGroup) {
        return scheduleGroupRepository.save(scheduleGroup);
    }

    @Override
    public ScheduleGroup findById(Long id) {
        return scheduleGroupRepository.findFirstById(id);
    }

    @Override
    public List<ScheduleGroup> findAllBySchool(Long schoolId) {
        return scheduleGroupRepository.findAllBySchoolClassSchoolId(schoolId);
    }

    @Override
    public List<ScheduleGroup> findAllByClass(Long classId) {
        return scheduleGroupRepository.findAllBySchoolClassId(classId);
    }

    @Override
    public List<ScheduleGroup> findAllByStudent(Long studentId) {
        return scheduleGroupRepository.findAllByStudentId(studentId);
    }

    @Override
    public void delete(Long id) {
        scheduleGroupRepository.deleteById(id);
    }
}
