package kg.kundoluk.school.service.school;

import kg.kundoluk.school.constants.CacheService;
import kg.kundoluk.school.dto.projection.SchoolDTO;
import kg.kundoluk.school.dto.school.SchoolRequest;
import kg.kundoluk.school.model.school.School;
import kg.kundoluk.school.repository.SchoolRepository;
import kg.kundoluk.school.utils.UpdateColumnUtil;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class SchoolService {
    private final SchoolRepository schoolRepository;

    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Cacheable(value = CacheService.SCHOOL)
    @Transactional(readOnly = true)
    public List<School> list(Boolean isTest) {
        return this.schoolRepository.findAllByIsTestOrderByNameAsc(isTest);
    }

    @Transactional(readOnly = true)
    public School get(@NonNull Long id) {
        return this.schoolRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @CacheEvict(value = CacheService.SCHOOL, allEntries = true)
    public void delete(@NonNull Long id) {
        this.schoolRepository.deleteById(id);
    }

    @CacheEvict(value = CacheService.SCHOOL, allEntries = true)
    public School create(@NonNull SchoolRequest schoolRequest) {
        final School school = School.builder()
                .name(schoolRequest.getName())
                .code(schoolRequest.getCode())
                .email(schoolRequest.getEmail())
                .phoneNumber(schoolRequest.getPhoneNumber())
                .studyDay(schoolRequest.getStudyDay())
                .schoolType(schoolRequest.getSchoolType())
                .schoolOrganizationType(schoolRequest.getSchoolOrganizationType())
                .schoolLanguageType(schoolRequest.getSchoolLanguageType())
                .gradeType(schoolRequest.getGradeType())
                .address(schoolRequest.getAddress())
                .isTest(false)
                .rayon(schoolRequest.getRayon())
                .region(schoolRequest.getRegion())
                .town(schoolRequest.getTown())
                .build();
        return this.schoolRepository.save(school);
    }

    @CacheEvict(value = CacheService.SCHOOL, allEntries = true)
    public School edit(@NonNull SchoolRequest schoolRequest, School school) {
        BeanUtils.copyProperties(schoolRequest, school, UpdateColumnUtil.getNullPropertyNames(schoolRequest));
        return schoolRepository.save(school);
    }

    @Transactional(readOnly = true)
    public SchoolDTO getSchoolInterface(Long id) {
        return schoolRepository.getSchoolInterface(id);
    }

    @Transactional(readOnly = true)
    public List<SchoolDTO> getByRayon(Long rayonId){
        return schoolRepository.getByRayonId(rayonId);
    }

    @Transactional(readOnly = true)
    public List<SchoolDTO> getByTown(Long townId){
        return schoolRepository.getByTownId(townId);
    }

}
