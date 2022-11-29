package kg.kundoluk.school.service.grade;

import kg.kundoluk.school.dto.projection.GradeParentCheckResponse;
import kg.kundoluk.school.dto.student.parent.GradeParentCheckRequest;

import java.time.LocalDate;
import java.util.List;

public interface GradeParentCheckService {
    void create(GradeParentCheckRequest gradeParentCheckRequest);
    List<GradeParentCheckResponse> getList(Long studentId, Long classId, LocalDate startDate, LocalDate endDate);
}
