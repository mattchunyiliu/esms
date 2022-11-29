package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.shift.ShiftCreateRequest;
import kg.kundoluk.school.dto.shift.ShiftRequest;
import kg.kundoluk.school.endpoint.ShiftEndpoint;
import kg.kundoluk.school.model.school.Shift;
import kg.kundoluk.school.repository.SchoolRepository;
import kg.kundoluk.school.service.school.ShiftService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Endpoint
public class ShiftEndpointImpl implements ShiftEndpoint {
    private final ShiftService service;
    private final SchoolRepository schoolRepository;

    public ShiftEndpointImpl(ShiftService service, SchoolRepository schoolRepository) {
        this.service = service;
        this.schoolRepository = schoolRepository;
    }

    @Override
    public Shift get(Long id) {
        return service.get(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        service.delete(id);
    }

    @Override
    public Shift create(ShiftCreateRequest shiftCreateRequest) {
        return service.create(toShiftRequest(shiftCreateRequest));
    }

    @Override
    public Shift edit(ShiftCreateRequest shiftCreateRequest, Shift shift) {
        return service.edit(toShiftRequest(shiftCreateRequest), shift);
    }

    @Override
    public List<Shift> listBySchool(Long schoolId) {
        return service.listBySchool(schoolId);
    }

    private ShiftRequest toShiftRequest(ShiftCreateRequest shiftCreateRequest){
        return ShiftRequest.builder().school(schoolRepository.getOne(shiftCreateRequest.getSchoolId())).title(shiftCreateRequest.getTitle()).build();
    }
}
