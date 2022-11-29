package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.grade.*;
import kg.kundoluk.school.dto.projection.GradeDTO;

import java.util.List;

public interface GradeEndpoint {
    void createBulkMobile(GradeMobileCreateDayRequest gradeMobileCreateDayRequest);
    void editBulkMobile(List<GradeMobileUpdateRequest> gradeMobileUpdateRequests);
    void deleteById(Long id);
    void deleteByTopic(Long topicId);
    List<GradeMobileResponse> dayFetch(GradeMobileResponseRequest gradeMobileResponseRequest);
    List<GradeMobileResponse> monthFetch(GradeMobileResponseDateRequest gradeMobileResponseDateRequest);
    List<GradeMobileResponse> studentFetch(GradeMobileResponseStudentRequest gradeMobileResponseStudentRequest);
    List<GradeMobileResponse> findAllByTopic(Long topicId);
    List<GradeMobileResponse> findAllByShiftTime(Long shiftTimeId);
}
