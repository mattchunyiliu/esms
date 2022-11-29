package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.section.*;
import kg.kundoluk.school.endpoint.SectionInstructorScheduleEndpoint;
import kg.kundoluk.school.model.section.SectionInstructor;
import kg.kundoluk.school.service.section.SectionInstructorService;
import kg.kundoluk.school.service.section.SectionScheduleService;

import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class SectionInstructorScheduleEndpointImpl implements SectionInstructorScheduleEndpoint {
    private final SectionInstructorService sectionInstructorService;
    private final SectionScheduleService sectionScheduleService;

    public SectionInstructorScheduleEndpointImpl(SectionInstructorService sectionInstructorService, SectionScheduleService sectionScheduleService) {
        this.sectionInstructorService = sectionInstructorService;
        this.sectionScheduleService = sectionScheduleService;
    }

    @Override
    public SectionInstructor create(SectionInstructorScheduleCreateRequest createRequest) {
        SectionInstructorCreateRequest sectionInstructorCreateRequest = new SectionInstructorCreateRequest(createRequest.getSectionId(), createRequest.getInstructorId());
        SectionInstructor sectionInstructor = sectionInstructorService.create(sectionInstructorCreateRequest);
        for (ScheduleWeekTime scheduleWeekTime: createRequest.getScheduleWeekTimeList()) {
            SectionScheduleRequest sectionScheduleRequest = new SectionScheduleRequest(sectionInstructor.getId(), scheduleWeekTime);
            sectionScheduleService.create(sectionScheduleRequest);
        }
        return sectionInstructor;
    }

    @Override
    public SectionInstructor edit(SectionInstructorScheduleUpdateRequest sectionInstructorScheduleUpdateRequest, SectionInstructor sectionInstructor) {
        SectionInstructorCreateRequest sectionInstructorCreateRequest = new SectionInstructorCreateRequest(sectionInstructor.getSection().getId(), sectionInstructorScheduleUpdateRequest.getInstructorId());
        for (ScheduleWeekTime scheduleWeekTime: sectionInstructorScheduleUpdateRequest.getScheduleWeekTimeList()){
            SectionScheduleRequest sectionScheduleRequest = new SectionScheduleRequest(sectionInstructor.getId(), scheduleWeekTime);
            sectionScheduleService.edit(sectionScheduleRequest, sectionScheduleService.findById(scheduleWeekTime.getId()));
        }
        return sectionInstructorService.edit(sectionInstructorCreateRequest, sectionInstructor);
    }

    @Override
    public List<SectionInstructorScheduleResponse> getBySchool(Long schoolId) {
        return sectionInstructorService.listBySchool(schoolId).stream().map(this::sectionInstructorScheduleResponse).collect(Collectors.toList());
    }

    @Override
    public List<SectionInstructorScheduleResponse> getBySection(Long sectionId) {
        return sectionInstructorService.listBySection(sectionId).stream().map(this::sectionInstructorScheduleResponse).collect(Collectors.toList());
    }

    @Override
    public void delete(Long sectionInstructorId) {
        sectionInstructorService.delete(sectionInstructorId);
    }

    @Override
    public List<SectionInstructorResponse> listByInstructor(Long instructorId) {
        return sectionInstructorService.listByInstructor(instructorId);
    }

    @Override
    public SectionInstructorResponse getBySectionInstructor(Long sectionInstructorId) {
        return sectionInstructorService.getById(sectionInstructorId);
    }

    private SectionInstructorScheduleResponse sectionInstructorScheduleResponse(SectionInstructor sectionInstructor){
        SectionInstructorScheduleResponse sectionInstructorResponse = new SectionInstructorScheduleResponse();
        sectionInstructorResponse.setSectionInstructorId(sectionInstructor.getId());
        sectionInstructorResponse.setSectionTitle(sectionInstructor.getSection().getTitle());
        sectionInstructorResponse.setPersonId(sectionInstructor.getPerson().getId());
        sectionInstructorResponse.setPersonTitle(sectionInstructor.getPerson().getSelectorTitle());
        sectionInstructorResponse.setScheduleWeekTimeList(sectionInstructor.getSchedules().stream().map(sectionSchedule -> new ScheduleWeekTime(sectionSchedule.getId(),sectionSchedule.getWeekDay(), sectionSchedule.getStartTime(), sectionSchedule.getEndTime())).collect(Collectors.toList()));
        return sectionInstructorResponse;
    }
}
