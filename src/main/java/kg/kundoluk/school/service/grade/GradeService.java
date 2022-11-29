package kg.kundoluk.school.service.grade;

import kg.kundoluk.school.dto.grade.GradeRequest;
import kg.kundoluk.school.dto.projection.GradeDTO;
import kg.kundoluk.school.model.grade.Grade;

import java.time.LocalDate;
import java.util.List;

public interface GradeService {
    Grade create(GradeRequest gradeRequest);
    Grade edit(GradeRequest gradeRequest, Grade grade);
    Grade findById(Long id);
    void deleteById(Long id);
    void deleteByTopic(Long topicId);
    List<Grade> createBulk(List<GradeRequest> gradeRequests);
    void deleteInBatch(List<Long> ids);
    List<GradeDTO> findAllByPersonCourseClassDateRange(Long personId, Long courseId, Long classId, LocalDate startDate, LocalDate endDate);
    List<GradeDTO> findAllByStudentCourseDateRange(Long studentId, Long courseId, LocalDate startDate, LocalDate endDate);
    List<GradeDTO> findAllByTopic(Long topicId);
    List<GradeDTO> findAllByShiftTime(Long shiftId);
}
