package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.projection.AnalyticClassGender;
import kg.kundoluk.school.dto.projection.AnalyticGender;

import java.util.List;

public interface StatisticEndpoint {
    List<AnalyticGender> getAllStudentGenderAnalytic(Long schoolId, Long rayonId, Long townId);
    List<AnalyticGender> getAllInstructorGenderAnalytic(Long schoolId, Long rayonId, Long townId);
    List<AnalyticGender> getAllParentGenderAnalytic(Long schoolId);
    List<AnalyticClassGender> getSchoolClassGenderAnalytic(Long schoolId, Long classId);
}
