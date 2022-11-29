package kg.kundoluk.school.service.school;

import kg.kundoluk.school.constants.CacheService;
import kg.kundoluk.school.dto.shift.ShiftRequest;
import kg.kundoluk.school.model.school.School;
import kg.kundoluk.school.model.school.Shift;
import kg.kundoluk.school.repository.ShiftRepository;
import lombok.NonNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ShiftService {
    private final ShiftRepository shiftRepository;

    public ShiftService(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    public Optional<Shift> get(@NonNull Long id) {
        return this.shiftRepository.findById(id);
    }

    public void delete(@NonNull Long id) {
        this.shiftRepository.deleteById(id);
    }

    public Shift create(@NonNull ShiftRequest shiftRequest) {
        final Shift shift = Shift.builder()
                .title(shiftRequest.getTitle())
                .school(shiftRequest.getSchool())
                .build();
        return this.shiftRepository.save(shift);
    }

    public Shift edit(@NonNull ShiftRequest shiftRequest, Shift shift) {
        return this.shiftRepository.save(
                shift
                        .setSchool(shiftRequest.getSchool())
                        .setTitle(shiftRequest.getTitle())
        );
    }

    public Boolean isExist(School school, String title){
        return shiftRepository.existsAllBySchoolAndTitle(school, title);
    }

    @Transactional(readOnly = true)
    public List<Shift> listBySchool(Long schoolId){
        return this.shiftRepository.findAllBySchoolId(schoolId);
    }
}
