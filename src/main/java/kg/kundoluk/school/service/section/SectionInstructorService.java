package kg.kundoluk.school.service.section;

import kg.kundoluk.school.dto.section.SectionInstructorCreateRequest;
import kg.kundoluk.school.dto.section.SectionInstructorResponse;
import kg.kundoluk.school.dto.section.SectionRequest;
import kg.kundoluk.school.model.section.Section;
import kg.kundoluk.school.model.section.SectionInstructor;
import kg.kundoluk.school.repository.PersonRepository;
import kg.kundoluk.school.repository.SectionInstructorRepository;
import kg.kundoluk.school.repository.SectionRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SectionInstructorService {
    private final SectionInstructorRepository sectionInstructorRepository;
    private final SectionRepository sectionRepository;
    private final PersonRepository personRepository;

    public SectionInstructorService(SectionInstructorRepository sectionInstructorRepository, SectionRepository sectionRepository, PersonRepository personRepository) {
        this.sectionInstructorRepository = sectionInstructorRepository;
        this.sectionRepository = sectionRepository;
        this.personRepository = personRepository;
    }

    public SectionInstructor create(@NonNull SectionInstructorCreateRequest sectionInstructorCreateRequest) {
        return this.sectionInstructorRepository.save(
                new SectionInstructor()
                        .setPerson(personRepository.getOne(sectionInstructorCreateRequest.getInstructorId()))
                        .setSection(sectionRepository.getOne(sectionInstructorCreateRequest.getSectionId()))
        );
    }

    public SectionInstructor edit(@NonNull SectionInstructorCreateRequest sectionInstructorCreateRequest, SectionInstructor sectionInstructor) {
        return this.sectionInstructorRepository.save(
                sectionInstructor
                        .setPerson(personRepository.getOne(sectionInstructorCreateRequest.getInstructorId()))
                        .setSection(sectionRepository.getOne(sectionInstructorCreateRequest.getSectionId()))
        );
    }

    public void delete(@NonNull Long id) {
        this.sectionInstructorRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<SectionInstructorResponse> listByInstructor(Long instructorId){
        return this.sectionInstructorRepository.findAllByPersonId(instructorId).stream().map(this::sectionInstructorResponse).collect(Collectors.toList());
    }

    public SectionInstructorResponse getById(Long id){
        return sectionInstructorResponse(sectionInstructorRepository.findFirstById(id));
    }

    @Transactional(readOnly = true)
    public List<SectionInstructor> listBySchool(Long schoolId){
        return this.sectionInstructorRepository.findAllBySectionSchoolId(schoolId);
    }

    @Transactional(readOnly = true)
    public List<SectionInstructor> listBySection(Long sectionId){
        return this.sectionInstructorRepository.findAllBySectionId(sectionId);
    }

    private SectionInstructorResponse sectionInstructorResponse(SectionInstructor sectionInstructor){
        SectionInstructorResponse sectionInstructorResponse = new SectionInstructorResponse();
        sectionInstructorResponse.setSectionInstructorId(sectionInstructor.getId());
        sectionInstructorResponse.setSectionTitle(sectionInstructor.getSection().getTitle());
        sectionInstructorResponse.setPersonId(sectionInstructor.getPerson().getId());
        sectionInstructorResponse.setPersonTitle(sectionInstructor.getPerson().getSelectorTitle());
        sectionInstructorResponse.setStudentCount(sectionInstructor.getStudents().size());
        return sectionInstructorResponse;
    }
}
