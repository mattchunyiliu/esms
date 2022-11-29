package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.constants.CacheService;
import kg.kundoluk.school.dto.projection.SchoolClassBaseDTO;
import kg.kundoluk.school.dto.projection.SchoolClassDTO;
import kg.kundoluk.school.dto.schoolClass.ClassCreateRequest;
import kg.kundoluk.school.dto.schoolClass.ClassRequest;
import kg.kundoluk.school.endpoint.SchoolClassEndpoint;
import kg.kundoluk.school.model.school.SchoolClass;
import kg.kundoluk.school.repository.LanguageRepository;
import kg.kundoluk.school.repository.PersonRepository;
import kg.kundoluk.school.repository.SchoolRepository;
import kg.kundoluk.school.repository.ShiftRepository;
import kg.kundoluk.school.service.school.SchoolClassService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolClassEndpointImpl implements SchoolClassEndpoint {

    private final SchoolClassService schoolClassService;
    private final SchoolRepository schoolRepository;
    private final ShiftRepository shiftRepository;
    private final LanguageRepository languageRepository;
    private final PersonRepository personRepository;

    public SchoolClassEndpointImpl(SchoolClassService schoolClassService, SchoolRepository schoolRepository, ShiftRepository shiftRepository, LanguageRepository languageRepository, PersonRepository personRepository) {
        this.schoolClassService = schoolClassService;
        this.schoolRepository = schoolRepository;
        this.shiftRepository = shiftRepository;
        this.languageRepository = languageRepository;
        this.personRepository = personRepository;
    }

    @Override
    public SchoolClassDTO get(Long id) {
        return schoolClassService.get(id);
    }

    @CacheEvict(value = CacheService.SCHOOL_CLASS, key = "#schoolId")
    @Override
    public void delete(Long id, Long schoolId) {
        schoolClassService.delete(id);
    }

    @CacheEvict(value = CacheService.SCHOOL_CLASS, key = "#classCreateRequest.schoolId")
    @Override
    public SchoolClass create(ClassCreateRequest classCreateRequest) {
        return schoolClassService.create(toClassRequest(classCreateRequest));
    }

    @CacheEvict(value = CacheService.SCHOOL_CLASS, key = "#classCreateRequest.schoolId")
    @Override
    public SchoolClass edit(ClassCreateRequest classCreateRequest, SchoolClass schoolClass) {
        return schoolClassService.edit(toClassRequest(classCreateRequest), schoolClass);
    }

    @Override
    public List<SchoolClassDTO> listBySchool(Long schoolId) {
        return schoolClassService.listBySchool(schoolId);
    }

    @Override
    public List<SchoolClassBaseDTO> listByPerson(Long personId) {
        return schoolClassService.listByPerson(personId);
    }

    @Cacheable(value = CacheService.SCHOOL_CLASS, key = "#schoolId")
    @Override
    public List<SchoolClass> findAllBySchool(Long schoolId) {
        return schoolClassService.findAllBySchool(schoolId);
    }

    private ClassRequest toClassRequest(ClassCreateRequest classCreateRequest){
        return ClassRequest.builder()
                .shift(classCreateRequest.getShiftId()!=null?shiftRepository.getOne(classCreateRequest.getShiftId()):null)
                .school(classCreateRequest.getSchoolId()!=null?schoolRepository.getOne(classCreateRequest.getSchoolId()):null)
                .label(classCreateRequest.getLabel())
                .classType(classCreateRequest.getClassType())
                .language(classCreateRequest.getLanguageId()!=null?languageRepository.getOne(classCreateRequest.getLanguageId()):null)
                .person(classCreateRequest.getPersonId()!=null?personRepository.getOne(classCreateRequest.getPersonId()):null)
                .level(classCreateRequest.getLevel())
                .build();
    }
}
