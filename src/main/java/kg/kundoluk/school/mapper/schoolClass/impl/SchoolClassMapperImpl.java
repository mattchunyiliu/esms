package kg.kundoluk.school.mapper.schoolClass.impl;

import kg.kundoluk.school.dto.schoolClass.SchoolClassResource;
import kg.kundoluk.school.mapper.schoolClass.SchoolClassMapper;
import kg.kundoluk.school.model.school.SchoolClass;
import org.springframework.stereotype.Component;

@Component
public class SchoolClassMapperImpl implements SchoolClassMapper {
    @Override
    public SchoolClassResource toSchoolClassResource(SchoolClass schoolClass) {
        SchoolClassResource resource = new SchoolClassResource();
        resource.setId(schoolClass.getId());
        resource.setClassLabel(schoolClass.getLabel());
        resource.setClassLevel(schoolClass.getLevel());
        if (schoolClass.getLanguage()!=null) {
            resource.setLanguage(schoolClass.getLanguage().getCode());
            resource.setLanguageTitle(schoolClass.getLanguage().getName());
        }
        if (schoolClass.getShift()!=null) {
            resource.setShift(schoolClass.getShift().getTitle());
            resource.setShiftId(schoolClass.getShift().getId());
        }
        if (schoolClass.getPerson()!=null)
            resource.setPersonTitle(schoolClass.getPerson().getSelectorTitle());
        return resource;
    }
}
