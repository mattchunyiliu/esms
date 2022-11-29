package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.projection.SchoolDTO;
import kg.kundoluk.school.dto.school.SchoolBaseDto;
import kg.kundoluk.school.dto.school.SchoolCreateRequest;
import kg.kundoluk.school.model.enums.SchoolOrganizationType;
import kg.kundoluk.school.model.school.School;

import java.util.List;

public interface SchoolEndpoint {
    School create(SchoolCreateRequest schoolCreateRequest);

    School edit(SchoolCreateRequest schoolCreateRequest, School school);

    School get(Long id);

    void delete(Long id);

    List<School> list(Boolean isTest);

    List<SchoolBaseDto> listBase();

    SchoolDTO getSchoolInterface(Long id);

    List<SchoolDTO> getByRayon(Long rayonId, SchoolOrganizationType schoolOrganizationType);

    List<SchoolDTO> getByTown(Long townId);
}
