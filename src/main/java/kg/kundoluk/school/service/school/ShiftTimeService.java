package kg.kundoluk.school.service.school;

import kg.kundoluk.school.constants.CacheService;
import kg.kundoluk.school.dto.projection.ShiftTimeViewDTO;
import kg.kundoluk.school.dto.shiftTime.ShiftTimeRequest;
import kg.kundoluk.school.model.school.ShiftTime;
import kg.kundoluk.school.repository.ShiftTimeRepository;
import lombok.NonNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ShiftTimeService {
    private final ShiftTimeRepository shiftTimeRepository;

    public ShiftTimeService(ShiftTimeRepository shiftTimeRepository) {
        this.shiftTimeRepository = shiftTimeRepository;
    }

    public ShiftTime get(@NonNull Long id) {
        return this.shiftTimeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void delete(@NonNull Long id) {
        this.shiftTimeRepository.deleteById(id);
    }
    
    public ShiftTime create(@NonNull ShiftTimeRequest shiftTimeRequest) {
        final ShiftTime shiftTime = ShiftTime.builder()
                .title(shiftTimeRequest.getTitle())
                .startAt(shiftTimeRequest.getStartAt())
                .shiftType(shiftTimeRequest.getShiftType())
                .endAt(shiftTimeRequest.getEndAt())
                .shift(shiftTimeRequest.getShift())
                .build();
        return this.shiftTimeRepository.save(shiftTime);
    }

    public ShiftTime edit(@NonNull ShiftTimeRequest shiftTimeRequest, ShiftTime shiftTime) {
        return this.shiftTimeRepository.save(
                shiftTime
                        .setShift(shiftTimeRequest.getShift())
                        .setShiftType(shiftTimeRequest.getShiftType())
                        .setStartAt(shiftTimeRequest.getStartAt())
                        .setEndAt(shiftTimeRequest.getEndAt())
                        .setTitle(shiftTimeRequest.getTitle())
        );
    }

    @Transactional(readOnly = true)
    public List<ShiftTime> listByShift(Long shiftId){
        return this.shiftTimeRepository.findAllByShiftId(shiftId);
    }

    @Transactional(readOnly = true)
    public List<ShiftTime> listBySchool(Long schoolId){
        return this.shiftTimeRepository.findAllBySchoolId(schoolId);
    }

    @Transactional(readOnly = true)
    public List<ShiftTimeViewDTO> listByShiftInterface(Long shiftId){
        return this.shiftTimeRepository.findAllByShiftInterface(shiftId);
    }
}
