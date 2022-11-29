package kg.kundoluk.school.service.schedule;

import kg.kundoluk.school.dto.projection.ScheduleClassLoadDTO;
import kg.kundoluk.school.dto.schedule.ScheduleClassLoadRequest;
import kg.kundoluk.school.model.schedule.ScheduleClassLoad;

import java.util.List;

public interface ScheduleClassLoadService {
    void create(ScheduleClassLoadRequest scheduleClassLoadRequest);
    void createBulk(List<ScheduleClassLoadRequest> scheduleClassLoadRequestList);
    void editHour(Long id, Integer hourLoad);
    void delete(Long id);
    List<ScheduleClassLoad> findAllBySchool(Long schoolId);
    List<ScheduleClassLoadDTO> findAllBySchoolInterface(Long schoolId);
}
