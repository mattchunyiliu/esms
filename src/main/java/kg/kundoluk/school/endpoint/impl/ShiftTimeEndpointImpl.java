package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.constants.CacheService;
import kg.kundoluk.school.dto.shiftTime.ShiftTimeCreateRequest;
import kg.kundoluk.school.dto.shiftTime.ShiftTimeRequest;
import kg.kundoluk.school.endpoint.ShiftTimeEndpoint;
import kg.kundoluk.school.model.school.ShiftTime;
import kg.kundoluk.school.repository.ShiftRepository;
import kg.kundoluk.school.service.school.ShiftTimeService;
import kg.kundoluk.school.utils.TimeHelper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Endpoint
public class ShiftTimeEndpointImpl implements ShiftTimeEndpoint {
    private final ShiftTimeService service;
    private final ShiftRepository shiftRepository;

    public ShiftTimeEndpointImpl(ShiftTimeService service, ShiftRepository shiftRepository) {
        this.service = service;
        this.shiftRepository = shiftRepository;
    }

    @Override
    public ShiftTime get(Long id) {
        return service.get(id);
    }

    @Override
    public void delete(Long id) {
        service.delete(id);
    }

    @Override
    public ShiftTime create(ShiftTimeCreateRequest shiftTimeCreateRequest) {
        return service.create(toShiftTimeRequest(shiftTimeCreateRequest));
    }

    @Override
    public void createBulk(List<ShiftTimeCreateRequest> shiftTimeCreateRequests) {
        for (ShiftTimeCreateRequest shiftTimeCreateRequest: shiftTimeCreateRequests){
            if (Objects.nonNull(shiftTimeCreateRequest.getId())){
                ShiftTime shiftTime = service.get(shiftTimeCreateRequest.getId());
                edit(shiftTimeCreateRequest, shiftTime);
            } else {
                create(shiftTimeCreateRequest);
            }
        }
    }

    @Override
    public ShiftTime edit(ShiftTimeCreateRequest shiftTimeCreateRequest, ShiftTime shiftTime) {
        return service.edit(toShiftTimeRequest(shiftTimeCreateRequest), shiftTime);
    }

    @Override
    public List<ShiftTime> listByShift(Long shiftId) {
        return service.listByShift(shiftId);
    }

    @Override
    public List<ShiftTimeCreateRequest> listBySchool(Long schoolId) {
        return service.listBySchool(schoolId).stream().map(this::toShiftTimeCreateRequest).collect(Collectors.toList());
    }

    private ShiftTimeRequest toShiftTimeRequest(ShiftTimeCreateRequest shiftTimeCreateRequest){
        return ShiftTimeRequest.builder()
                .shift(shiftRepository.getOne(shiftTimeCreateRequest.getShiftId()))
                .shiftType(shiftTimeCreateRequest.getShiftType())
                .title(shiftTimeCreateRequest.getTitle())
                .startAt(LocalTime.parse(shiftTimeCreateRequest.getStartTime(), TimeHelper.TIME_FORMATTER))
                .endAt(LocalTime.parse(shiftTimeCreateRequest.getEndTime(), TimeHelper.TIME_FORMATTER))
                .build();
    }

    private ShiftTimeCreateRequest toShiftTimeCreateRequest(ShiftTime shiftTime){
        ShiftTimeCreateRequest shiftTimeCreateRequest = new ShiftTimeCreateRequest();
        shiftTimeCreateRequest.setId(shiftTime.getId());
        shiftTimeCreateRequest.setStartTime(TimeHelper.TIME_FORMATTER.format(shiftTime.getStartAt()));
        shiftTimeCreateRequest.setEndTime(TimeHelper.TIME_FORMATTER.format(shiftTime.getEndAt()));
        shiftTimeCreateRequest.setTitle(shiftTime.getTitle());
        shiftTimeCreateRequest.setShiftType(shiftTime.getShiftType());
        shiftTimeCreateRequest.setShiftId(shiftTime.getShift().getId());
        shiftTimeCreateRequest.setShiftTitle(shiftTime.getShift().getTitle());
        return shiftTimeCreateRequest;
    }
}
