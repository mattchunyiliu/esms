package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.projection.GradeParentCheckResponse;
import kg.kundoluk.school.dto.student.parent.GradeParentCheckCreateRequest;

import java.util.List;

public interface GradeParentCheckEndpoint {
    void create(GradeParentCheckCreateRequest gradeParentCheckCreateRequest);
    List<GradeParentCheckResponse> getList(Long studentId, Long classId, String startDate, String endDate);
}
