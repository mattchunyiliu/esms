package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.model.student.CardAttendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CardAttendanceEndpoint {
    void create(Long studentId, Integer type);
    Page<CardAttendance> getByStudent(Long studentId, Pageable pageable);
}
