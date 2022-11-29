package kg.kundoluk.school.service.async;

import kg.kundoluk.school.dto.schedule.ScheduleCreateRequest;
import kg.kundoluk.school.exception.AlreadyExistException;
import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.school.SchoolClass;
import kg.kundoluk.school.model.school.SchoolCourse;

public interface StudentCourseGenerateService {
    void generateCourse(ScheduleCreateRequest scheduleCreateRequest, Person person, SchoolCourse schoolCourse, SchoolClass schoolClass) throws AlreadyExistException;
    void generateStudentCourse(Long studentId, Long classId) throws AlreadyExistException;
}
