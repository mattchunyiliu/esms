package kg.kundoluk.school.service.schedule;

import kg.kundoluk.school.dto.schedule.ScheduleGroupRequest;
import kg.kundoluk.school.model.schedule.ScheduleGroup;

import java.util.List;

public interface ScheduleGroupService {
    ScheduleGroup create(ScheduleGroupRequest scheduleGroupRequest);
    ScheduleGroup edit(ScheduleGroupRequest scheduleGroupRequest, ScheduleGroup scheduleGroup);
    ScheduleGroup save(ScheduleGroup scheduleGroup);
    ScheduleGroup findById(Long id);
    List<ScheduleGroup> findAllBySchool(Long schoolId);
    List<ScheduleGroup> findAllByClass(Long classId);
    List<ScheduleGroup> findAllByStudent(Long studentId);
    void delete(Long id);
}
