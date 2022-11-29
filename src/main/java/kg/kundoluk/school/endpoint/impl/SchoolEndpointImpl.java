package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.projection.SchoolDTO;
import kg.kundoluk.school.dto.school.SchoolBaseDto;
import kg.kundoluk.school.dto.school.SchoolCreateRequest;
import kg.kundoluk.school.dto.school.SchoolRequest;
import kg.kundoluk.school.endpoint.SchoolEndpoint;
import kg.kundoluk.school.model.enums.SchoolOrganizationType;
import kg.kundoluk.school.model.school.School;
import kg.kundoluk.school.repository.location.RayonRepository;
import kg.kundoluk.school.repository.location.RegionRepository;
import kg.kundoluk.school.repository.location.TownRepository;
import kg.kundoluk.school.service.school.SchoolService;

import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class SchoolEndpointImpl implements SchoolEndpoint {
    private final SchoolService schoolService;
    private final RayonRepository rayonRepository;
    private final RegionRepository regionRepository;
    private final TownRepository townRepository;

    public SchoolEndpointImpl(SchoolService schoolService, RayonRepository rayonRepository, RegionRepository regionRepository, TownRepository townRepository) {
        this.schoolService = schoolService;
        this.rayonRepository = rayonRepository;
        this.regionRepository = regionRepository;
        this.townRepository = townRepository;
    }

    @Override
    public School create(SchoolCreateRequest schoolCreateRequest) {
        return schoolService.create(toSchoolRequest(schoolCreateRequest));
    }

    @Override
    public School edit(SchoolCreateRequest schoolCreateRequest, School school) {
        return schoolService.edit(toSchoolRequest(schoolCreateRequest), school);
    }

    @Override
    public School get(Long id) {
        return schoolService.get(id);
    }

    @Override
    public void delete(Long id) {
        schoolService.delete(id);
    }

    @Override
    public List<School> list(Boolean isTest) {
        return schoolService.list(isTest);
    }

    @Override
    public List<SchoolBaseDto> listBase() {
        return schoolService.list(false).stream().map(this::toSchoolBaseDto).collect(Collectors.toList());
    }

    @Override
    public SchoolDTO getSchoolInterface(Long id) {
        return schoolService.getSchoolInterface(id);
    }

    @Override
    public List<SchoolDTO> getByRayon(Long rayonId, SchoolOrganizationType schoolOrganizationType) {
        return schoolService.getByRayon(rayonId);
    }

    @Override
    public List<SchoolDTO> getByTown(Long townId) {
        return schoolService.getByTown(townId);
    }

    private SchoolRequest toSchoolRequest(SchoolCreateRequest schoolCreateRequest) {
        return SchoolRequest
                .builder()
                .code(schoolCreateRequest.getCode())
                .email(schoolCreateRequest.getEmail())
                .phoneNumber(schoolCreateRequest.getPhoneNumber())
                .schoolType(schoolCreateRequest.getSchoolType())
                .schoolOrganizationType(schoolCreateRequest.getSchoolOrganizationType())
                .schoolLanguageType(schoolCreateRequest.getSchoolLanguageType())
                .gradeType(schoolCreateRequest.getGradeType())
                .name(schoolCreateRequest.getName())
                .studyDay(schoolCreateRequest.getStudyDay())
                .rayon(schoolCreateRequest.getRayonId()!=null?rayonRepository.getOne(schoolCreateRequest.getRayonId()):null)
                .region(schoolCreateRequest.getRegionId()!=null?regionRepository.getOne(schoolCreateRequest.getRegionId()):null)
                .town(schoolCreateRequest.getTownId()!=null?townRepository.getOne(schoolCreateRequest.getTownId()):null)
                .address(schoolCreateRequest.getAddress())
                .isTest(schoolCreateRequest.getIsTest())
                .build();
    }

    private SchoolBaseDto toSchoolBaseDto(School school){
        SchoolBaseDto schoolBaseDto = new SchoolBaseDto();
        schoolBaseDto.setId(school.getId());
        schoolBaseDto.setName(school.getName());
        schoolBaseDto.setAddress(school.getRayon().getId().toString());
        return schoolBaseDto;
    }
}
