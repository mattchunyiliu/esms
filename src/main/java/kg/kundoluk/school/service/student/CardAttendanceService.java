package kg.kundoluk.school.service.student;

import kg.kundoluk.school.dto.student.CardAttendanceRequest;
import kg.kundoluk.school.model.student.CardAttendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CardAttendanceService {
    CardAttendance create(CardAttendanceRequest cardAttendanceRequest);
    Page<CardAttendance> getByStudent(Long studentId, Pageable pageable);
}
