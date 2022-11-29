package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.grade.QuarterGradeCreateRequest;
import kg.kundoluk.school.dto.grade.QuarterGradeMobileResponse;
import kg.kundoluk.school.dto.grade.QuarterGradeMobileResponseRequest;
import kg.kundoluk.school.dto.grade.QuarterGradeUpdateRequest;
import kg.kundoluk.school.model.grade.QuarterGrade;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface QuarterGradeEndpoint {
    QuarterGrade create(QuarterGradeCreateRequest quarterGradeCreateRequest);
    QuarterGrade edit(QuarterGradeUpdateRequest quarterGradeUpdateRequest, QuarterGrade quarterGrade);
    void delete(Long id);
    List<QuarterGradeMobileResponse> findAllByChronicle(@RequestBody QuarterGradeMobileResponseRequest gradeMobileCreateDayRequest);
    List<QuarterGradeMobileResponse> findAllByStudentChronicle(Long studentId, Long chronicleId);
}
