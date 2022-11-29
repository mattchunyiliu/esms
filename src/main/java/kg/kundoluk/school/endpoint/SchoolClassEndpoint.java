package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.projection.SchoolClassBaseDTO;
import kg.kundoluk.school.dto.projection.SchoolClassDTO;
import kg.kundoluk.school.dto.schoolClass.ClassCreateRequest;
import kg.kundoluk.school.model.school.SchoolClass;

import java.util.List;

public interface SchoolClassEndpoint {
    SchoolClassDTO get(Long id);
    void delete(Long id, Long schoolId);
    SchoolClass create(ClassCreateRequest classCreateRequest);
    SchoolClass edit(ClassCreateRequest classCreateRequest, SchoolClass schoolClass);
    List<SchoolClassDTO> listBySchool(Long schoolId);
    List<SchoolClassBaseDTO> listByPerson(Long personId);
    List<SchoolClass> findAllBySchool(Long schoolId);
}
