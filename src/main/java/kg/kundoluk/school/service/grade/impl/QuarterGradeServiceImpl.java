package kg.kundoluk.school.service.grade.impl;

import kg.kundoluk.school.dto.grade.QuarterGradeRequest;
import kg.kundoluk.school.dto.projection.QuarterGradeDTO;
import kg.kundoluk.school.model.grade.QuarterGrade;
import kg.kundoluk.school.repository.QuarterGradeRepository;
import kg.kundoluk.school.service.grade.QuarterGradeService;
import kg.kundoluk.school.utils.UpdateColumnUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuarterGradeServiceImpl implements QuarterGradeService {
    private final QuarterGradeRepository quarterGradeRepository;

    public QuarterGradeServiceImpl(QuarterGradeRepository quarterGradeRepository) {
        this.quarterGradeRepository = quarterGradeRepository;
    }

    @Override
    public QuarterGrade create(QuarterGradeRequest gradeRequest) {
        QuarterGrade quarterGrade = new QuarterGrade()
                .setGradeMarkType(gradeRequest.getGradeMarkType())
                .setQuarter(gradeRequest.getQuarter())
                .setComment(gradeRequest.getComment())
                .setMark(gradeRequest.getMark())
                .setPerson(gradeRequest.getPerson())
                .setSchoolClass(gradeRequest.getSchoolClass())
                .setStudentCourse(gradeRequest.getStudentCourse());
        return quarterGradeRepository.save(quarterGrade);
    }

    @Override
    public QuarterGrade edit(QuarterGradeRequest gradeRequest, QuarterGrade quarterGrade) {
        BeanUtils.copyProperties(gradeRequest, quarterGrade, UpdateColumnUtil.getNullPropertyNames(gradeRequest));
        return quarterGradeRepository.save(quarterGrade);
    }

    @Override
    public QuarterGrade findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        quarterGradeRepository.deleteById(id);
    }

    @Override
    public List<QuarterGradeDTO> list(Long personId, Long courseId, Long classId, Long chronicleId) {
        return quarterGradeRepository.getAllByPerson(personId, courseId, classId, chronicleId);
    }

    @Override
    public List<QuarterGradeDTO> listByStudent(Long studentId, Long chronicleId) {
        return quarterGradeRepository.getAllByStudent(studentId, chronicleId);
    }

    @Override
    public List<QuarterGrade> createBulk(List<QuarterGradeRequest> gradeRequests) {
        List<QuarterGrade> quarterGrades = new ArrayList<>();
        for (QuarterGradeRequest gradeRequest: gradeRequests){
            quarterGrades.add(
                    new QuarterGrade()
                            .setGradeMarkType(gradeRequest.getGradeMarkType())
                            .setQuarter(gradeRequest.getQuarter())
                            .setComment(gradeRequest.getComment())
                            .setMark(gradeRequest.getMark())
                            .setPerson(gradeRequest.getPerson())
                            .setSchoolClass(gradeRequest.getSchoolClass())
                            .setStudentCourse(gradeRequest.getStudentCourse())
            );
        }
        return quarterGradeRepository.saveAll(quarterGrades);
    }

    @Override
    public void deleteInBatch(List<Long> ids) {
        quarterGradeRepository.deleteAllByIdIn(ids);
    }
}
