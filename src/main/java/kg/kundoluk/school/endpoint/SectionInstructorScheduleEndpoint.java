package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.section.SectionInstructorResponse;
import kg.kundoluk.school.dto.section.SectionInstructorScheduleCreateRequest;
import kg.kundoluk.school.dto.section.SectionInstructorScheduleResponse;
import kg.kundoluk.school.dto.section.SectionInstructorScheduleUpdateRequest;
import kg.kundoluk.school.model.section.SectionInstructor;

import java.util.List;

public interface SectionInstructorScheduleEndpoint {
    SectionInstructor create(SectionInstructorScheduleCreateRequest createRequest);
    SectionInstructor edit(SectionInstructorScheduleUpdateRequest sectionInstructorScheduleUpdateRequest, SectionInstructor sectionInstructor);
    List<SectionInstructorScheduleResponse> getBySchool(Long schoolId);
    List<SectionInstructorScheduleResponse> getBySection(Long sectionId);
    void delete(Long sectionInstructorId);
    List<SectionInstructorResponse> listByInstructor(Long instructorId);
    SectionInstructorResponse getBySectionInstructor(Long sectionInstructorId);
}
