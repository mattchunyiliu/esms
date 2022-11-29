package kg.kundoluk.school.service.grade.impl;

import kg.kundoluk.school.dto.projection.GradeParentCheckResponse;
import kg.kundoluk.school.dto.student.parent.GradeParentCheckRequest;
import kg.kundoluk.school.model.grade.GradeParentCheck;
import kg.kundoluk.school.repository.GradeParentCheckRepository;
import kg.kundoluk.school.service.grade.GradeParentCheckService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class GradeParentCheckServiceImpl implements GradeParentCheckService {
    private final GradeParentCheckRepository gradeParentCheckRepository;

    public GradeParentCheckServiceImpl(GradeParentCheckRepository gradeParentCheckRepository) {
        this.gradeParentCheckRepository = gradeParentCheckRepository;
    }

    @Transactional
    @Override
    public void create(GradeParentCheckRequest gradeParentCheckRequest) {
        GradeParentCheck gradeParentCheck = new GradeParentCheck()
                .setChecked(true)
                .setStartDate(gradeParentCheckRequest.getStartDate())
                .setEndDate(gradeParentCheckRequest.getEndDate())
                .setStudent(gradeParentCheckRequest.getStudent())
                .setUser(gradeParentCheckRequest.getUser())
                .setWeekNumber(gradeParentCheckRequest.getWeekNumber());
        gradeParentCheckRepository.save(gradeParentCheck);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GradeParentCheckResponse> getList(Long studentId, Long classId, LocalDate startDate, LocalDate endDate) {
        return gradeParentCheckRepository.getList(studentId, classId, startDate, endDate);
    }
}
