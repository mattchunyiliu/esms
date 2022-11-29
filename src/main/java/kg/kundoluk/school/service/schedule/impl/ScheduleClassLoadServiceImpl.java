package kg.kundoluk.school.service.schedule.impl;

import kg.kundoluk.school.dto.projection.ScheduleClassLoadDTO;
import kg.kundoluk.school.dto.schedule.ScheduleClassLoadRequest;
import kg.kundoluk.school.model.schedule.ScheduleClassLoad;
import kg.kundoluk.school.repository.ScheduleClassLoadRepository;
import kg.kundoluk.school.service.schedule.ScheduleClassLoadService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleClassLoadServiceImpl implements ScheduleClassLoadService {
    private final ScheduleClassLoadRepository scheduleClassLoadRepository;

    public ScheduleClassLoadServiceImpl(ScheduleClassLoadRepository scheduleClassLoadRepository) {
        this.scheduleClassLoadRepository = scheduleClassLoadRepository;
    }

    @Override
    public void create(ScheduleClassLoadRequest scheduleClassLoadRequest) {
        ScheduleClassLoad scheduleClassLoad = ScheduleClassLoad.builder()
                .hourLoad(scheduleClassLoadRequest.getHourCount())
                .person(scheduleClassLoadRequest.getPerson())
                .schoolClass(scheduleClassLoadRequest.getSchoolClass())
                .schoolCourse(scheduleClassLoadRequest.getSchoolCourse())
                .build();
        scheduleClassLoadRepository.save(scheduleClassLoad);
    }

    @Override
    public void createBulk(List<ScheduleClassLoadRequest> scheduleClassLoadRequestList) {
        List<ScheduleClassLoad> scheduleClassLoads = new ArrayList<>();
        for (ScheduleClassLoadRequest scheduleClassLoadRequest: scheduleClassLoadRequestList){
            scheduleClassLoads.add(ScheduleClassLoad.builder()
                    .hourLoad(scheduleClassLoadRequest.getHourCount())
                    .person(scheduleClassLoadRequest.getPerson())
                    .schoolClass(scheduleClassLoadRequest.getSchoolClass())
                    .schoolCourse(scheduleClassLoadRequest.getSchoolCourse())
                    .build());
        }
        scheduleClassLoadRepository.saveAll(scheduleClassLoads);
    }

    @Override
    public void editHour(Long id, Integer hourLoad) {
        scheduleClassLoadRepository.updateHour(id, hourLoad);
    }

    @Override
    public void delete(Long id) {
        scheduleClassLoadRepository.deleteById(id);
    }

    @Override
    public List<ScheduleClassLoad> findAllBySchool(Long schoolId) {
        return scheduleClassLoadRepository.findAllBySchoolClassSchoolId(schoolId);
    }

    @Override
    public List<ScheduleClassLoadDTO> findAllBySchoolInterface(Long schoolId) {
        return scheduleClassLoadRepository.findAllBySchoolInterface(schoolId);
    }
}
