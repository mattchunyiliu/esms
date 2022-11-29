package kg.kundoluk.school.service.section;

import kg.kundoluk.school.dto.section.SectionRequest;
import kg.kundoluk.school.model.section.Section;
import kg.kundoluk.school.repository.SectionRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Transactional
@Service
public class SectionService {
    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public Section get(@NonNull Long id) {
        return this.sectionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void delete(@NonNull Long id) {
        this.sectionRepository.deleteById(id);
    }

    public Section create(@NonNull SectionRequest sectionRequest) {
        final Section section = Section.builder()
                .title(sectionRequest.getTitle())
                .description(sectionRequest.getDescription())
                .school(sectionRequest.getSchool())
                .build();
        return this.sectionRepository.save(section);
    }

    public Section edit(@NonNull SectionRequest sectionUpdateRequest, Section section) {
        return this.sectionRepository.save(
                section
                        .setTitle(sectionUpdateRequest.getTitle())
                        .setDescription(sectionUpdateRequest.getDescription())
        );
    }

    @Transactional(readOnly = true)
    public List<Section> listBySchool(Long schoolId){
        return this.sectionRepository.findAllBySchoolId(schoolId);
    }
}
