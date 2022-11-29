package kg.kundoluk.school.service.school;

import kg.kundoluk.school.dto.projection.SchoolClassBaseDTO;
import kg.kundoluk.school.dto.projection.SchoolClassDTO;
import kg.kundoluk.school.dto.schoolClass.ClassRequest;
import kg.kundoluk.school.model.school.SchoolClass;
import kg.kundoluk.school.repository.SchoolClassRepository;
import kg.kundoluk.school.utils.UpdateColumnUtil;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Transactional
@Service
public class SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;

    public SchoolClassService(SchoolClassRepository schoolClassRepository) {
        this.schoolClassRepository = schoolClassRepository;
    }

    public SchoolClass findById(@NonNull Long id) {
        return this.schoolClassRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public SchoolClassDTO get(@NonNull Long id) {
        return this.schoolClassRepository.findByIdProjection(id);
    }

    public void delete(@NonNull Long id) {
        this.schoolClassRepository.deleteById(id);
    }

    public SchoolClass create(@NonNull ClassRequest classRequest) {
        final SchoolClass schoolClass = SchoolClass.builder()
                .label(classRequest.getLabel())
                .shift(classRequest.getShift())
                .classType(classRequest.getClassType())
                .language(classRequest.getLanguage())
                .level(classRequest.getLevel())
                .person(classRequest.getPerson())
                .school(classRequest.getSchool())
                .build();
        return this.schoolClassRepository.save(schoolClass);
    }

    public SchoolClass edit(@NonNull ClassRequest classRequest, SchoolClass schoolClass) {
        BeanUtils.copyProperties(classRequest, schoolClass, UpdateColumnUtil.getNullPropertyNames(classRequest));
        return schoolClassRepository.save(schoolClass);
    }

    @Transactional(readOnly = true)
    public List<SchoolClassDTO> listBySchool(Long schoolId){
        return this.schoolClassRepository.findBySchoolSQL(schoolId);
    }

    public List<SchoolClassBaseDTO> listByPerson(Long personId){
        return this.schoolClassRepository.findByPersonSQL(personId);
    }

    @Transactional(readOnly = true)
    public List<SchoolClass> findAllBySchool(Long schoolId){
        return this.schoolClassRepository.findAllBySchoolIdOrderByLevelDesc(schoolId);
    }

    public SchoolClass save(SchoolClass schoolClass){
        return this.schoolClassRepository.save(schoolClass);
    }

}
