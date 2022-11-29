package kg.kundoluk.school.service.section;

import kg.kundoluk.school.dto.projection.SectionScheduleDTO;
import kg.kundoluk.school.dto.section.ScheduleWeekTime;
import kg.kundoluk.school.dto.section.SectionScheduleRequest;
import kg.kundoluk.school.model.section.SectionSchedule;
import kg.kundoluk.school.repository.SectionInstructorRepository;
import kg.kundoluk.school.repository.SectionScheduleRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SectionScheduleService {
    private final SectionScheduleRepository sectionScheduleRepository;
    private final SectionInstructorRepository sectionInstructorRepository;

    public SectionScheduleService(SectionScheduleRepository sectionScheduleRepository, SectionInstructorRepository sectionInstructorRepository) {
        this.sectionScheduleRepository = sectionScheduleRepository;
        this.sectionInstructorRepository = sectionInstructorRepository;
    }

    public SectionSchedule create(@NonNull SectionScheduleRequest sectionScheduleRequest) {
        ScheduleWeekTime scheduleWeekTime = sectionScheduleRequest.getScheduleWeekTime();
        final SectionSchedule sectionSchedule = SectionSchedule.builder()
                .sectionInstructor(sectionInstructorRepository.getOne(sectionScheduleRequest.getSectionInstructorId()))
                .weekDay(scheduleWeekTime.getWeekDay())
                .startTime(scheduleWeekTime.getStartTime())
                .endTime(scheduleWeekTime.getEndTime())
                .build();
        return this.sectionScheduleRepository.save(sectionSchedule);
    }

    public SectionSchedule edit(@NonNull SectionScheduleRequest sectionScheduleRequest, SectionSchedule sectionSchedule) {
        return this.sectionScheduleRepository.save(
                sectionSchedule
                        .setWeekDay(sectionScheduleRequest.getScheduleWeekTime().getWeekDay())
                        .setStartTime(sectionScheduleRequest.getScheduleWeekTime()
                        .getStartTime()).setEndTime(sectionScheduleRequest.getScheduleWeekTime().getEndTime())
        );
    }

    public SectionSchedule findById(Long id){
        return this.sectionScheduleRepository.findById(id).orElse(null);
    }

    public void delete(@NonNull Long id) {
        this.sectionScheduleRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<SectionScheduleDTO> listBySectionInstructor(Long id){
        return this.sectionScheduleRepository.findAllBySectionInstructorId(id);
    }
}
