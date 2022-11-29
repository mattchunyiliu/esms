package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.projection.AnalyticSchoolCount;
import kg.kundoluk.school.dto.projection.AssignmentInstructorClass;
import kg.kundoluk.school.dto.projection.GradeCountAnalytic;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AssignmentAnalyticEndpoint {
    List<GradeCountAnalytic> getInstructorAssignmentCount(Long schoolId, String start, String end);
    List<AnalyticSchoolCount> getSchoolAssignmentCount(Long rayonId, Long townId, String start, String end);
    List<AssignmentInstructorClass> getInstructorClassList(Long instructorId, Long classId);
}
