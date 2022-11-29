package kg.kundoluk.school.service.schedule.impl;

import kg.kundoluk.school.constants.CacheService;
import kg.kundoluk.school.dto.projection.ScheduleInstructorDTO;
import kg.kundoluk.school.dto.schedule.ScheduleRequest;
import kg.kundoluk.school.exception.AlreadyExistException;
import kg.kundoluk.school.model.enums.WeekDay;
import kg.kundoluk.school.model.schedule.Schedule;
import kg.kundoluk.school.repository.ScheduleRepository;
import kg.kundoluk.school.service.schedule.ScheduleService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    /*@Caching(evict = {
            @CacheEvict(value = CacheService.SCHEDULE_INSTRUCTOR, key = "#scheduleRequest.person.id"),
            @CacheEvict(value = CacheService.SCHEDULE_SCHOOL, allEntries = true)
    })*/
    @Override
    public Schedule create(ScheduleRequest scheduleRequest) throws AlreadyExistException {

        Boolean isExist = scheduleRepository.existsBySchoolClassAndWeekDayAndShiftTime(scheduleRequest.getSchoolClass(), scheduleRequest.getWeekDay(), scheduleRequest.getShiftTime());
        if (!isExist || scheduleRequest.getIsGroup()) {
            return saveScheduleRequest(scheduleRequest);
        } else throw new AlreadyExistException("CLASS_EXIST");
    }

    /*@Caching(evict = {
            @CacheEvict(value = CacheService.SCHEDULE_INSTRUCTOR, allEntries = true),
            @CacheEvict(value = CacheService.SCHEDULE_SCHOOL, allEntries = true)
    })*/
    @Override
    public void createMassive(List<ScheduleRequest> scheduleRequestList) {
        for (ScheduleRequest scheduleRequest: scheduleRequestList){
            saveScheduleRequest(scheduleRequest);
        }
    }

    private Schedule saveScheduleRequest(ScheduleRequest scheduleRequest) {
        Schedule schedule = Schedule.builder()
                .schoolCourse(scheduleRequest.getSchoolCourse())
                .schoolClass(scheduleRequest.getSchoolClass())
                .scheduleGroup(scheduleRequest.getScheduleGroup())
                .person(scheduleRequest.getPerson())
                .shiftTime(scheduleRequest.getShiftTime())
                .isGroup(scheduleRequest.getIsGroup())
                .groupTitle(scheduleRequest.getGroupTitle())
                .weekDay(scheduleRequest.getWeekDay())
                .build();
        return scheduleRepository.save(schedule);
    }

    /*@Caching(evict = {
            @CacheEvict(value = CacheService.SCHEDULE_INSTRUCTOR, key = "#scheduleRequest.person.id"),
            @CacheEvict(value = CacheService.SCHEDULE_SCHOOL, allEntries = true)
    })*/
    @Override
    public Schedule edit(ScheduleRequest scheduleRequest, Schedule schedule) {
        schedule
                .setPerson(scheduleRequest.getPerson())
                .setSchoolCourse(scheduleRequest.getSchoolCourse())
                .setGroupTitle(scheduleRequest.getGroupTitle())
                .setScheduleGroup(scheduleRequest.getScheduleGroup())
                .setIsGroup(scheduleRequest.getIsGroup());
        return scheduleRepository.save(schedule);
    }

    @Override
    public Schedule findById(Long id) {
        return scheduleRepository.findFirstById(id);
    }

    @Override
    public void delete(Long id) {
        scheduleRepository.deleteById(id);
    }

    /*@Caching(evict = {
            @CacheEvict(value = CacheService.SCHEDULE_INSTRUCTOR, allEntries = true),
            @CacheEvict(value = CacheService.SCHEDULE_SCHOOL, key = "#schoolId")
    })*/
    @Override
    public void deleteBySchool(Long schoolId, Long shiftId) {
        scheduleRepository.massDelete(schoolId, shiftId);
    }

    //@Cacheable(value = CacheService.SCHEDULE_SCHOOL, key = "#schoolId")
    @Override
    public List<Schedule> listAllBySchool(Long schoolId) {
        return scheduleRepository.findAllBySchoolClassSchoolId(schoolId);
    }

    @Override
    public List<Schedule> listAllByClass(Long classId) {
        return scheduleRepository.findAllBySchoolClassId(classId);
    }

    //@Cacheable(value = CacheService.SCHEDULE_INSTRUCTOR, key = "#instructorId")
    @Override
    public List<Schedule> listAllByInstructorAndDay(Long instructorId, WeekDay weekDay) {
        return scheduleRepository.findAllByPersonIdAndWeekDay(instructorId, weekDay);
    }

    @Override
    public List<Schedule> listAllByClassAndDay(Long classId, WeekDay weekDay) {
        return scheduleRepository.findAllBySchoolClassIdAndWeekDay(classId, weekDay);
    }

    //@Cacheable(value = CacheService.SCHEDULE_INSTRUCTOR, key = "#instructorId")
    @Override
    public List<Schedule> listAllByInstructor(Long instructorId) {
        return scheduleRepository.findAllByPersonId(instructorId);
    }

    @Override
    public List<ScheduleInstructorDTO> listAllByInstructorClassCourse(Long instructorId, Long classId, Long courseId) {
        return scheduleRepository.findAllByPersonClassCourse(instructorId, classId, courseId);
    }
}
