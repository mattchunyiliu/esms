package kg.kundoluk.school.service.grade;

import kg.kundoluk.school.dto.grade.QuarterGradeRequest;
import kg.kundoluk.school.dto.projection.QuarterGradeDTO;
import kg.kundoluk.school.model.grade.QuarterGrade;

import java.util.List;

public interface QuarterGradeService {
    QuarterGrade create(QuarterGradeRequest gradeRequest);
    QuarterGrade edit(QuarterGradeRequest gradeRequest, QuarterGrade quarterGrade);
    QuarterGrade findById(Long id);
    void deleteById(Long id);
    List<QuarterGradeDTO> list(Long personId, Long courseId, Long classId, Long chronicleId);
    List<QuarterGradeDTO> listByStudent(Long studentId, Long chronicleId);
    List<QuarterGrade> createBulk(List<QuarterGradeRequest> gradeRequests);
    void deleteInBatch(List<Long> ids);
}
