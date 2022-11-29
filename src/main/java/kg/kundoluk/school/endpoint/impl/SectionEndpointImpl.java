package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.section.*;
import kg.kundoluk.school.endpoint.SectionEndpoint;
import kg.kundoluk.school.model.section.Section;
import kg.kundoluk.school.model.section.SectionInstructor;
import kg.kundoluk.school.repository.SchoolRepository;
import kg.kundoluk.school.service.section.SectionService;

import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class SectionEndpointImpl implements SectionEndpoint {
    private final SectionService sectionService;
    private final SchoolRepository schoolRepository;

    public SectionEndpointImpl(SectionService sectionService, SchoolRepository schoolRepository) {
        this.sectionService = sectionService;
        this.schoolRepository = schoolRepository;
    }

    @Override
    public Section get(Long id) {
        return sectionService.get(id);
    }

    @Override
    public void delete(Long id) {
        sectionService.delete(id);
    }

    @Override
    public Section create(SectionCreateRequest sectionCreateRequest) {
        SectionRequest sectionRequest = SectionRequest.builder()
                .title(sectionCreateRequest.getTitle())
                .description(sectionCreateRequest.getDescription())
                .school(schoolRepository.getOne(sectionCreateRequest.getSchoolId()))
                .build();
        return sectionService.create(sectionRequest);
    }

    @Override
    public Section edit(SectionUpdateRequest sectionUpdateRequest, Section section) {
        SectionRequest sectionRequest = SectionRequest.builder()
                .title(sectionUpdateRequest.getTitle())
                .description(sectionUpdateRequest.getDescription())
                .build();
        return sectionService.edit(sectionRequest, section);
    }

    @Override
    public List<SectionInstructorViewResponse> listBySchool(Long schoolId) {
        return sectionService.listBySchool(schoolId).stream().map(this::sectionInstructorScheduleResponse).collect(Collectors.toList());
    }

    private SectionInstructorViewResponse sectionInstructorScheduleResponse(Section section){
        SectionInstructorViewResponse sectionInstructorViewResponse = new SectionInstructorViewResponse();
        sectionInstructorViewResponse.setSectionId(section.getId());
        sectionInstructorViewResponse.setSectionTitle(section.getTitle());
        sectionInstructorViewResponse.setSectionInstructorResponses(section.getSectionInstructors().stream().map(sectionInstructor -> new SectionInstructorResponse(sectionInstructor.getId(), sectionInstructor.getPerson().getSelectorTitle(), sectionInstructor.getStudents().size())).collect(Collectors.toList()));
        return sectionInstructorViewResponse;
    }
}
