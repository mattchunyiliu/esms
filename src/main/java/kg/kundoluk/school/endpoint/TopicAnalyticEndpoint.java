package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.projection.AnalyticSchoolCount;
import kg.kundoluk.school.dto.projection.CalendarTopicClass;
import kg.kundoluk.school.dto.projection.GradeCountAnalytic;

import java.util.List;

public interface TopicAnalyticEndpoint {
    List<GradeCountAnalytic> getInstructorTopicCount(Long schoolId, String start, String end);
    List<CalendarTopicClass> getInstructorClassList(Long instructorId, Long classId);
    List<AnalyticSchoolCount> getTopicCountBySchool(Long rayonId, String start, String end);
}
