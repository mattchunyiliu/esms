package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.section.SectionCreateRequest;
import kg.kundoluk.school.dto.section.SectionInstructorViewResponse;
import kg.kundoluk.school.dto.section.SectionUpdateRequest;
import kg.kundoluk.school.model.section.Section;

import java.util.List;

public interface SectionEndpoint {
    Section get(Long id);
    void delete(Long id);
    Section create(SectionCreateRequest sectionCreateRequest);
    Section edit(SectionUpdateRequest sectionUpdateRequest, Section section);
    List<SectionInstructorViewResponse> listBySchool(Long schoolId);
}
