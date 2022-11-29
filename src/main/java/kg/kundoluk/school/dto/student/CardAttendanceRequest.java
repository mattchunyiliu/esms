package kg.kundoluk.school.dto.student;

import kg.kundoluk.school.model.student.Student;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CardAttendanceRequest {
    Student student;
    LocalDateTime attendanceDate;
    Integer attendanceType;
}
