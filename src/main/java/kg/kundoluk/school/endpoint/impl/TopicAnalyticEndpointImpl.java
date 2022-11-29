package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.projection.AnalyticSchoolCount;
import kg.kundoluk.school.dto.projection.CalendarTopicClass;
import kg.kundoluk.school.dto.projection.GradeCountAnalytic;
import kg.kundoluk.school.endpoint.TopicAnalyticEndpoint;
import kg.kundoluk.school.repository.TopicAnalyticRepository;
import kg.kundoluk.school.utils.TimeHelper;

import java.time.LocalDate;
import java.util.List;

@Endpoint
public class TopicAnalyticEndpointImpl implements TopicAnalyticEndpoint {
    private final TopicAnalyticRepository topicAnalyticRepository;

    public TopicAnalyticEndpointImpl(TopicAnalyticRepository topicAnalyticRepository) {
        this.topicAnalyticRepository = topicAnalyticRepository;
    }

    @Override
    public List<GradeCountAnalytic> getInstructorTopicCount(Long schoolId, String start, String end) {
        LocalDate startDate = LocalDate.parse(start, TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate endDate = LocalDate.parse(end, TimeHelper.DATE_REVERSE_FORMATTER);
        return topicAnalyticRepository.getInstructorTopicCount(schoolId, startDate, endDate);
    }

    @Override
    public List<CalendarTopicClass> getInstructorClassList(Long instructorId, Long classId) {
        return topicAnalyticRepository.getInstructorClassList(instructorId, classId);
    }

    @Override
    public List<AnalyticSchoolCount> getTopicCountBySchool(Long rayonId, String start, String end) {
        LocalDate startDate = LocalDate.parse(start, TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate endDate = LocalDate.parse(end, TimeHelper.DATE_REVERSE_FORMATTER);
        return topicAnalyticRepository.getTopicCountBySchool(rayonId, startDate, endDate);
    }
}
