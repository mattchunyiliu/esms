package kg.kundoluk.school.service.schedule;

import kg.kundoluk.school.dto.projection.ScheduleInstructorDTO;
import kg.kundoluk.school.dto.schedule.ScheduleRequest;
import kg.kundoluk.school.exception.AlreadyExistException;
import kg.kundoluk.school.model.enums.WeekDay;
import kg.kundoluk.school.model.schedule.Schedule;

import java.util.List;

public interface ScheduleService {
    Schedule create(ScheduleRequest scheduleRequest) throws AlreadyExistException;
    void createMassive(List<ScheduleRequest> scheduleRequestList);
    Schedule edit(ScheduleRequest scheduleRequest, Schedule schedule);
    Schedule findById(Long id);
    void delete(Long id);
    void deleteBySchool(Long schoolId, Long shiftId);
    List<Schedule> listAllBySchool(Long schoolId);
    List<Schedule> listAllByClass(Long classId);
    List<Schedule> listAllByInstructorAndDay(Long instructorId, WeekDay weekDay);
    List<Schedule> listAllByClassAndDay(Long classId, WeekDay weekDay);
    List<Schedule> listAllByInstructor(Long instructorId);
    List<ScheduleInstructorDTO> listAllByInstructorClassCourse(Long instructorId, Long classId, Long courseId);
}
