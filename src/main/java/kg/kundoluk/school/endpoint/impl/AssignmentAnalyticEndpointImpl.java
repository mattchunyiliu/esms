package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.projection.AnalyticSchoolCount;
import kg.kundoluk.school.dto.projection.AssignmentInstructorClass;
import kg.kundoluk.school.dto.projection.GradeCountAnalytic;
import kg.kundoluk.school.endpoint.AssignmentAnalyticEndpoint;
import kg.kundoluk.school.repository.AssignmentAnalyticRepository;
import kg.kundoluk.school.utils.TimeHelper;

import java.time.LocalDate;
import java.util.List;

@Endpoint
public class AssignmentAnalyticEndpointImpl implements AssignmentAnalyticEndpoint {
    private final AssignmentAnalyticRepository assignmentAnalyticRepository;

    public AssignmentAnalyticEndpointImpl(AssignmentAnalyticRepository assignmentAnalyticRepository) {
        this.assignmentAnalyticRepository = assignmentAnalyticRepository;
    }

    @Override
    public List<GradeCountAnalytic> getInstructorAssignmentCount(Long schoolId, String start, String end) {
        LocalDate startDate = LocalDate.parse(start, TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate endDate = LocalDate.parse(end, TimeHelper.DATE_REVERSE_FORMATTER);
        return assignmentAnalyticRepository.getInstructorAssignmentCount(schoolId, startDate, endDate);
    }

    @Override
    public List<AnalyticSchoolCount> getSchoolAssignmentCount(Long rayonId, Long townId, String start, String end) {
        LocalDate startDate = LocalDate.parse(start, TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate endDate = LocalDate.parse(end, TimeHelper.DATE_REVERSE_FORMATTER);
        if (townId == null)
            townId = 0L;
        return assignmentAnalyticRepository.getAssignmentCountBySchool(rayonId, townId, startDate, endDate);
    }

    @Override
    public List<AssignmentInstructorClass> getInstructorClassList(Long instructorId, Long classId) {
        return assignmentAnalyticRepository.getAssignmentByInstructorClass(instructorId, classId);
    }
}
