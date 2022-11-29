package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.person.*;
import kg.kundoluk.school.dto.projection.PersonViewDTO;
import kg.kundoluk.school.dto.user.UserSearch;
import kg.kundoluk.school.exception.PermissionException;
import kg.kundoluk.school.exception.ResourceNotFoundException;
import kg.kundoluk.school.model.Person;

import java.util.List;

public interface PersonEndpoint {
    Person create(PersonCreateRequest personCreateRequest);
    Person edit(PersonUpdateRequest personUpdateRequest, Person person);
    PersonUserDto findById(Long id);
    PersonUserDto findByUserId(Long userId);
    PersonUserDto findByUsername(String username);
    void delete(Person person, Long schoolId) throws PermissionException;
    List<InstructorDto> instructorList(Long schoolId, Boolean archived);
    List<PersonUserDto> findAllBySchoolAndRole(Long schoolId, Long roleId);
    void activate(Person person);
    List<PersonSearchResult> searchPerson(UserSearch userSearch) throws ResourceNotFoundException;
}
