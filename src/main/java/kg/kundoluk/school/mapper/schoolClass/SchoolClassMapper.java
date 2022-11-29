package kg.kundoluk.school.mapper.schoolClass;

import kg.kundoluk.school.dto.schoolClass.SchoolClassResource;
import kg.kundoluk.school.model.school.SchoolClass;

public interface SchoolClassMapper {
    SchoolClassResource toSchoolClassResource(SchoolClass schoolClass);
}
