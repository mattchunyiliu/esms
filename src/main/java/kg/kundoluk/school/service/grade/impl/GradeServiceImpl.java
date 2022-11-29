package kg.kundoluk.school.service.grade.impl;

import kg.kundoluk.school.dto.grade.GradeRequest;
import kg.kundoluk.school.dto.projection.GradeDTO;
import kg.kundoluk.school.model.grade.Grade;
import kg.kundoluk.school.repository.GradeRepository;
import kg.kundoluk.school.service.grade.GradeService;
import kg.kundoluk.school.utils.UpdateColumnUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;

    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public Grade create(GradeRequest gradeRequest) {
        Grade grade = new Grade()
                .setGradeDate(gradeRequest.getGradeDate())
                .setGradeMarkType(gradeRequest.getGradeMarkType())
                .setCalendarTopic(gradeRequest.getCalendarTopic())
                .setMark(gradeRequest.getMark())
                .setPerson(gradeRequest.getPerson())
                .setShiftTime(gradeRequest.getShiftTime())
                .setStudentCourse(gradeRequest.getStudentCourse());
        return gradeRepository.save(grade);
    }

    @Override
    public Grade edit(GradeRequest gradeRequest, Grade grade) {
        BeanUtils.copyProperties(gradeRequest, grade, UpdateColumnUtil.getNullPropertyNames(gradeRequest));
        return gradeRepository.save(grade);
    }

    @Override
    public Grade findById(Long id) {
        return gradeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        gradeRepository.deleteById(id);
    }

    @Override
    public void deleteByTopic(Long topicId) {
        gradeRepository.deleteAllByTopic(topicId);
    }

    @Override
    public List<Grade> createBulk(List<GradeRequest> gradeRequests) {
        List<Grade> grades = new ArrayList<>();
        for (GradeRequest gradeRequest: gradeRequests){
            grades.add(
                    new Grade()
                    .setGradeDate(gradeRequest.getGradeDate())
                    .setGradeMarkType(gradeRequest.getGradeMarkType())
                    .setCalendarTopic(gradeRequest.getCalendarTopic())
                    .setMark(gradeRequest.getMark())
                    .setPerson(gradeRequest.getPerson())
                    .setShiftTime(gradeRequest.getShiftTime())
                    .setStudentCourse(gradeRequest.getStudentCourse()));
        }
        return gradeRepository.saveAll(grades);
    }

    @Override
    public void deleteInBatch(List<Long> ids) {
        List<Grade> grades = gradeRepository.findAllByIdIn(ids);
        gradeRepository.deleteInBatch(grades);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GradeDTO> findAllByPersonCourseClassDateRange(Long personId, Long courseId, Long classId, LocalDate startDate, LocalDate endDate) {
        return gradeRepository.findAllByPersonCourseClassDateRange(personId, courseId, classId, startDate, endDate);
    }

    @Override
    public List<GradeDTO> findAllByStudentCourseDateRange(Long studentId, Long courseId, LocalDate startDate, LocalDate endDate) {
        return gradeRepository.findAllByStudentCourseDateRange(studentId, courseId, startDate, endDate);
    }

    @Override
    public List<GradeDTO> findAllByTopic(Long topicId) {
        return gradeRepository.findAllByCalendarTopicId(topicId);
    }

    @Override
    public List<GradeDTO> findAllByShiftTime(Long shiftId) {
        return gradeRepository.findAllByShiftTimeId(shiftId);
    }
}
