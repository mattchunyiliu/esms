package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.schedule.ScheduleClassLoadCreateRequest;
import kg.kundoluk.school.dto.schedule.ScheduleClassLoadResponse;
import kg.kundoluk.school.dto.schedule.ScheduleClassLoadUpdateRequest;
import kg.kundoluk.school.model.schedule.ScheduleClassLoad;

import java.util.List;

public interface ScheduleClassLoadEndpoint {
    void create(ScheduleClassLoadCreateRequest scheduleClassLoadCreateRequest);
    void createBulk(List<ScheduleClassLoadCreateRequest> scheduleClassLoadCreateRequests);
    void editBulk(List<ScheduleClassLoadUpdateRequest> scheduleClassLoadUpdateRequests);
    void delete(Long id);
    List<ScheduleClassLoadResponse> findAllBySchool(Long schoolId);
}
