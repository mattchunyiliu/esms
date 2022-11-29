package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.schedule.ScheduleCreateRequest;
import kg.kundoluk.school.dto.schedule.ScheduleMonthResponse;
import kg.kundoluk.school.dto.schedule.ScheduleResponse;
import kg.kundoluk.school.dto.schedule.ScheduleUpdateRequest;
import kg.kundoluk.school.exception.AlreadyExistException;
import kg.kundoluk.school.model.schedule.Schedule;

import java.util.List;

public interface ScheduleEndpoint {
    void create(ScheduleCreateRequest scheduleCreateRequest) throws AlreadyExistException;
    void createMassive(List<ScheduleCreateRequest> scheduleCreateRequestList);
    void edit(ScheduleUpdateRequest scheduleUpdateRequest, Schedule schedule);
    void delete(Long id);
    void deleteBySchool(Long schoolId, Long shiftId);
    ScheduleResponse findById(Long id);
    List<ScheduleResponse> findAllBySchool(Long schoolId);
    List<ScheduleResponse> findAllByClass(Long classId);
    List<ScheduleResponse> findAllByInstructorDate(Long instructorId, String date);
    List<ScheduleResponse> findAllByClassDate(Long classId, String date);
    List<ScheduleResponse> findAllByInstructor(Long instructorId);
    List<ScheduleMonthResponse> findAllInstructorScheduleMonth(Long instructorId, Long classId, Long courseId, String startDate, String endDate);
    List<ScheduleMonthResponse> findAllClassScheduleMonth(Long classId, Long courseId, String startDate, String endDate);
}
