package kg.kundoluk.school.service.school;

import kg.kundoluk.school.dto.quarter.QuarterRequest;
import kg.kundoluk.school.model.school.Quarter;
import kg.kundoluk.school.repository.QuarterRepository;
import kg.kundoluk.school.utils.UpdateColumnUtil;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Transactional
@Service
public class QuarterService {
    private final QuarterRepository quarterRepository;

    public QuarterService(QuarterRepository quarterRepository) {
        this.quarterRepository = quarterRepository;
    }

    public Quarter get(@NonNull Long id) {
        return this.quarterRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void delete(@NonNull Long id) {
        this.quarterRepository.deleteById(id);
    }

    //@CacheEvict(value = CacheService.QUARTER, key = "quarterRequest.school.id")
    public Quarter create(@NonNull QuarterRequest quarterRequest) {
        final Quarter quarter = Quarter.builder()
                .quarter(quarterRequest.getTitle())
                .startDate(quarterRequest.getStartDate())
                .endDate(quarterRequest.getEndDate())
                .chronicle(quarterRequest.getChronicleYear())
                .school(quarterRequest.getSchool())
                .status(quarterRequest.getStatus())
                .build();
        return this.quarterRepository.save(quarter);
    }

    //@CacheEvict(value = CacheService.QUARTER, key = "quarterRequest.school.id")
    public Quarter edit(@NonNull QuarterRequest quarterRequest, Quarter quarter) {
        BeanUtils.copyProperties(quarterRequest, quarter, UpdateColumnUtil.getNullPropertyNames(quarterRequest));
        return this.quarterRepository.save(quarter);
    }

    //@Cacheable(value = CacheService.QUARTER, key = "schoolId")
    @Transactional(readOnly = true)
    public List<Quarter> listBySchoolChronicle(Long schoolId, Long chronicleId){
         return quarterRepository.findAllBySchoolIdAndChronicleId(schoolId, chronicleId);
    }
}
