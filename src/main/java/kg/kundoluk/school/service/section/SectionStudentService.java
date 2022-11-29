package kg.kundoluk.school.service.section;

import kg.kundoluk.school.dto.section.SectionStudentRequest;
import kg.kundoluk.school.dto.section.SectionStudentResponse;
import kg.kundoluk.school.dto.student.StudentBasicInfo;
import kg.kundoluk.school.model.section.SectionStudent;
import kg.kundoluk.school.repository.SectionInstructorRepository;
import kg.kundoluk.school.repository.SectionStudentRepository;
import kg.kundoluk.school.repository.StudentRepository;
import kg.kundoluk.school.service.references.ChronicleService;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SectionStudentService {
    private final SectionStudentRepository sectionStudentRepository;
    private final SectionInstructorRepository sectionInstructorRepository;
    private final StudentRepository studentRepository;
    private final ChronicleService chronicleService;

    public SectionStudentService(SectionStudentRepository sectionStudentRepository, SectionInstructorRepository sectionInstructorRepository, StudentRepository studentRepository, ChronicleService chronicleService) {
        this.sectionStudentRepository = sectionStudentRepository;
        this.sectionInstructorRepository = sectionInstructorRepository;
        this.studentRepository = studentRepository;
        this.chronicleService = chronicleService;
    }

    public SectionStudent create(@NonNull SectionStudentRequest sectionStudentRequest) {
        SectionStudent sectionStudent = sectionStudentRepository.findSectionStudentBySectionInstructorIdAndStudentIdAndChronicleYearId(sectionStudentRequest.getSectionInstructorId(), sectionStudentRequest.getStudentId(), sectionStudentRequest.getChronicleId());
        if (sectionStudent == null) {
            return this.sectionStudentRepository.save(
                    new SectionStudent()
                            .setSectionInstructor(sectionInstructorRepository.getOne(sectionStudentRequest.getSectionInstructorId()))
                            .setStudent(studentRepository.getOne(sectionStudentRequest.getStudentId()))
                            .setChronicleYear(chronicleService.getActive())
            );
        }
        return sectionStudent;
    }

    public SectionStudent edit(@NonNull SectionStudentRequest sectionStudentRequest, SectionStudent sectionStudent) {
        return this.sectionStudentRepository.save(
                sectionStudent
                        .setSectionInstructor(sectionInstructorRepository.getOne(sectionStudentRequest.getSectionInstructorId()))
                        .setStudent(studentRepository.getOne(sectionStudentRequest.getStudentId()))
        );
    }

    public void delete(@NonNull Long id) {
        this.sectionStudentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<StudentBasicInfo> listBySectionInstructor(Long sectionInstructorId){
        return this.sectionStudentRepository.findAllBySectionInstructorId(sectionInstructorId).stream().map(this::studentBasicInfo).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SectionStudentResponse> listByStudent(Long studentId, Long chronicleId){
        return this.sectionStudentRepository.findAllByStudentIdAndChronicleYearId(studentId, chronicleId).stream().map(this::sectionStudentResponse).collect(Collectors.toList());
    }

    private StudentBasicInfo studentBasicInfo(SectionStudent sectionStudent){
        StudentBasicInfo studentBasicInfo = new StudentBasicInfo();
        studentBasicInfo.setId(sectionStudent.getId());
        studentBasicInfo.setStudentId(sectionStudent.getStudent().getId());
        studentBasicInfo.setStudentTitle(sectionStudent.getStudent().getSelectorTitle());
        studentBasicInfo.setClassTitle(sectionStudent.getStudent().getSchoolClass().getSelectorTitle());
        studentBasicInfo.setAvatar(sectionStudent.getStudent().getUser().getAvatar());
        return studentBasicInfo;
    }

    private SectionStudentResponse sectionStudentResponse(SectionStudent sectionStudent){
        SectionStudentResponse sectionStudentResponse = new SectionStudentResponse();
        sectionStudentResponse.setId(sectionStudent.getId());
        sectionStudentResponse.setSectionInstructorId(sectionStudent.getSectionInstructor().getId());
        sectionStudentResponse.setSectionTitle(sectionStudent.getSectionInstructor().getSection().getTitle());
        sectionStudentResponse.setPersonTitle(sectionStudent.getSectionInstructor().getPerson().getSelectorTitle());
        return sectionStudentResponse;
    }
}
