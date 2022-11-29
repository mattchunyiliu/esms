package kg.kundoluk.school.service.section;

import kg.kundoluk.school.dto.section.SectionStudentGradeCreateRequest;
import kg.kundoluk.school.dto.section.SectionStudentGradeResponse;
import kg.kundoluk.school.dto.section.SectionStudentGradeUpdateRequest;
import kg.kundoluk.school.dto.student.StudentBasicInfo;
import kg.kundoluk.school.model.section.SectionStudent;
import kg.kundoluk.school.model.section.SectionStudentGrade;
import kg.kundoluk.school.repository.SectionStudentGradeRepository;
import kg.kundoluk.school.repository.SectionStudentRepository;
import kg.kundoluk.school.utils.TimeHelper;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SectionStudentGradeService {
    private final SectionStudentGradeRepository sectionStudentGradeRepository;
    private final SectionStudentRepository sectionStudentRepository;

    public SectionStudentGradeService(SectionStudentGradeRepository sectionStudentGradeRepository, SectionStudentRepository sectionStudentRepository) {
        this.sectionStudentGradeRepository = sectionStudentGradeRepository;
        this.sectionStudentRepository = sectionStudentRepository;
    }

    public SectionStudentGrade create(@NonNull SectionStudentGradeCreateRequest sectionStudentGradeCreateRequest) {
        return this.sectionStudentGradeRepository.save(
                new SectionStudentGrade()
                        .setSectionStudent(sectionStudentRepository.getOne(sectionStudentGradeCreateRequest.getSectionStudentId()))
                        .setMark(sectionStudentGradeCreateRequest.getMark())
                        .setGradeDate(LocalDate.parse(sectionStudentGradeCreateRequest.getGradeDate(), TimeHelper.DATE_REVERSE_FORMATTER))
                        .setTopic(sectionStudentGradeCreateRequest.getTopic())
        );
    }

    public SectionStudentGrade update(@NonNull SectionStudentGradeUpdateRequest sectionStudentGradeUpdateRequest, SectionStudentGrade sectionStudentGrade) {
        return this.sectionStudentGradeRepository.save(
                sectionStudentGrade
                        .setMark(sectionStudentGradeUpdateRequest.getMark())
                        .setGradeDate(LocalDate.parse(sectionStudentGradeUpdateRequest.getGradeDate(), TimeHelper.DATE_REVERSE_FORMATTER))
                        .setTopic(sectionStudentGradeUpdateRequest.getTopic())
        );
    }

    public void delete(@NonNull Long id) {
        this.sectionStudentGradeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<SectionStudentGradeResponse> listBySectionInstructor(Long sectionInstructorId, String start, String end){
        LocalDate startDate = LocalDate.parse(start, TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate endDate = LocalDate.parse(end, TimeHelper.DATE_REVERSE_FORMATTER);
        return this.sectionStudentGradeRepository.findAllBySectionInstructorId(sectionInstructorId, startDate, endDate).stream().map(this::sectionStudentGradeResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SectionStudentGradeResponse> listBySectionStudent(Long studentId, Long sectionInstructorId, String start, String end){
        LocalDate startDate = LocalDate.parse(start, TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate endDate = LocalDate.parse(end, TimeHelper.DATE_REVERSE_FORMATTER);
        return this.sectionStudentGradeRepository.findAllBySectionStudentId(studentId, sectionInstructorId, startDate, endDate).stream().map(this::sectionStudentGradeResponse).collect(Collectors.toList());
    }

    private SectionStudentGradeResponse sectionStudentGradeResponse(SectionStudentGrade sectionStudentGrade){
        SectionStudentGradeResponse sectionStudentGradeResponse = new SectionStudentGradeResponse();
        sectionStudentGradeResponse.setGradeId(sectionStudentGrade.getId());
        sectionStudentGradeResponse.setGradeDate(TimeHelper.DATE_REVERSE_FORMATTER.format(sectionStudentGrade.getGradeDate()));
        sectionStudentGradeResponse.setSectionStudentId(sectionStudentGrade.getSectionStudent().getId());
        sectionStudentGradeResponse.setMark(sectionStudentGrade.getMark());
        sectionStudentGradeResponse.setStudentTitle(sectionStudentGrade.getSectionStudent().getStudent().getSelectorTitle());
        return sectionStudentGradeResponse;
    }
}
