package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.schedule.ScheduleGroupCreateRequest;
import kg.kundoluk.school.dto.schedule.ScheduleGroupResponse;
import kg.kundoluk.school.dto.schedule.ScheduleGroupSingleResponse;
import kg.kundoluk.school.dto.schedule.ScheduleGroupUpdateRequest;
import kg.kundoluk.school.model.schedule.ScheduleGroup;

import java.util.List;

public interface ScheduleGroupEndpoint {
    ScheduleGroup create(ScheduleGroupCreateRequest scheduleGroupCreateRequest);
    ScheduleGroup edit(ScheduleGroupUpdateRequest scheduleGroupUpdateRequest, ScheduleGroup scheduleGroup);
    ScheduleGroupSingleResponse findById(Long id);
    List<ScheduleGroupResponse> findAllBySchool(Long schoolId);
    List<ScheduleGroupResponse> findAllByClass(Long classId);
    List<ScheduleGroupResponse> findAllByStudent(Long studentId);
    void delete(Long id, Long schoolId);
}
