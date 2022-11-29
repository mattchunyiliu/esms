package kg.kundoluk.school.service.person;

import kg.kundoluk.school.dto.person.PersonRequest;
import kg.kundoluk.school.dto.projection.PersonViewDTO;
import kg.kundoluk.school.model.Person;

import java.time.LocalDate;
import java.util.List;

public interface PersonService {
    Person create(PersonRequest personRequest);
    Person edit(PersonRequest personRequest, Person person);
    Person findById(Long id);
    Person findByUserId(Long userId);
    Person findByUsername(String username);
    List<Person> findByTitle(String firstName, String lastName, String middleName, Integer gender, LocalDate dob);
    List<Person> findByTitle(String firstName, String lastName, String middleName);
    Boolean delete(Person person);
    void save(Person person);
    List<Person> instructorList(Long schoolId, Boolean archived);
    List<Person> findAllBySchoolAndRole(Long schoolId, Long roleId);
}
