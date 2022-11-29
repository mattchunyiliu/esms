package kg.kundoluk.school.service.school;

import kg.kundoluk.school.dto.projection.SchoolPresetDTO;
import kg.kundoluk.school.dto.school.SchoolPresetDto;
import kg.kundoluk.school.model.references.ChronicleYear;
import kg.kundoluk.school.model.school.School;
import kg.kundoluk.school.model.school.SchoolPreset;
import kg.kundoluk.school.repository.ChronicleRepository;
import kg.kundoluk.school.repository.SchoolPresetRepository;
import kg.kundoluk.school.repository.SchoolRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Transactional
@Service
public class SchoolPresetService {
    private final SchoolPresetRepository schoolPresetRepository;
    private final SchoolRepository schoolRepository;
    private final ChronicleRepository chronicleRepository;

    public SchoolPresetService(SchoolPresetRepository schoolPresetRepository, SchoolRepository schoolRepository, ChronicleRepository chronicleRepository) {
        this.schoolPresetRepository = schoolPresetRepository;
        this.schoolRepository = schoolRepository;
        this.chronicleRepository = chronicleRepository;
    }

    public SchoolPreset create(SchoolPresetDto schoolPresetDto) {
        if (isExist(schoolPresetDto.getSchoolId(), schoolPresetDto.getChronicleId()) != null)
            return null;
        School school = schoolRepository.getOne(schoolPresetDto.getSchoolId());
        ChronicleYear chronicleYear = chronicleRepository.getOne(schoolPresetDto.getChronicleId());
        return schoolPresetRepository.save(new SchoolPreset().setChronicleYear(chronicleYear).setSchool(school).setIsPreset(schoolPresetDto.getIsPreset()).setStepNumber(schoolPresetDto.getStepNumber()));
    }

    public SchoolPreset findById(Long id){
        return schoolPresetRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public SchoolPreset update(SchoolPreset schoolPreset, SchoolPresetDto schoolPresetDto) {
        return schoolPresetRepository.save(
                schoolPreset
                        .setIsPreset(schoolPresetDto.getIsPreset())
                        .setStepNumber(schoolPresetDto.getStepNumber())
                        .setIsClassRaised(schoolPresetDto.getIsClassRaised())
        );
    }

    @Transactional(readOnly = true)
    public SchoolPreset getBySchool(Long schoolId, Long chronicleId) {
        return schoolPresetRepository.findFirstBySchoolIdAndChronicleYearId(schoolId, chronicleId);
    }

    @Transactional(readOnly = true)
    public SchoolPresetDTO isExist(Long schoolId, Long chronicleId) {
        return schoolPresetRepository.findBySchoolChronicle(schoolId, chronicleId);
    }

    @Transactional(readOnly = true)
    public List<SchoolPresetDTO> listByChronicle(Long chronicleId) {
        return schoolPresetRepository.findAllByChronicle(chronicleId);
    }
}
