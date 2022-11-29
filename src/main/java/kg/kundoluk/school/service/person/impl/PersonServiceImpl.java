package kg.kundoluk.school.service.person.impl;

import kg.kundoluk.school.dto.person.PersonRequest;
import kg.kundoluk.school.dto.projection.PersonViewDTO;
import kg.kundoluk.school.exception.ResourceNotFoundException;
import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.enums.Gender;
import kg.kundoluk.school.repository.PersonRepository;
import kg.kundoluk.school.service.person.PersonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person create(PersonRequest personRequest) {
       return saveOrUpdate(personRequest, new Person());
    }

    @Override
    public Person edit(PersonRequest personRequest, Person person) {
        return saveOrUpdate(personRequest, person);
    }

    @Transactional(readOnly = true)
    @Override
    public Person findById(Long id) {
        return personRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Person findByUserId(Long userId) {
        return personRepository.findFirstByUserId(userId).orElse(null);
    }

    @Override
    public Person findByUsername(String username) {
        return personRepository.findFirstByUserUsername(username).orElse(null);
    }

    @Override
    public List<Person> findByTitle(String firstName, String lastName, String middleName, Integer gender, LocalDate dob) {
        if(StringUtils.isEmpty(middleName))
            middleName = "-";
        return personRepository.findByTitle(firstName.toLowerCase(), lastName.toLowerCase(), middleName.toLowerCase(), Gender.values()[gender], dob);
    }

    @Override
    public List<Person> findByTitle(String firstName, String lastName, String middleName) {
        return personRepository.findByTitle(firstName.toLowerCase(), lastName.toLowerCase(), middleName.toLowerCase());
    }

    @Transactional(propagation = Propagation.NEVER)
    @Override
    public Boolean delete(Person person) {
        try {
            this.personRepository.delete(person);
            return true;
        } catch (Exception e){
            personRepository.save(person.setArchived(true));
            return false;
        }
    }

    @Override
    public void save(Person person) {
        this.personRepository.save(person);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Person> instructorList(Long schoolId, Boolean archived) {
        return personRepository.instructorList(schoolId, archived);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Person> findAllBySchoolAndRole(Long schoolId, Long roleId) {
        return personRepository.findAllBySchoolAndRole(schoolId, roleId);
    }

    private Person saveOrUpdate(PersonRequest personRequest, Person person){
        if (personRequest.getUser()!=null)
            person.setUser(personRequest.getUser());
        person
                .setPhone(personRequest.getPhone())
                .setArchived(false)
                .setJob(personRequest.getJob())
                .setJobPlace(personRequest.getJobPlace())
                .setAvatar(personRequest.getAvatar())
                .setFirstName(personRequest.getFirstName())
                .setLastName(personRequest.getLastName())
                .setMiddleName(personRequest.getMiddleName())
                .setGender(personRequest.getGender())
                .setBirthday(personRequest.getBirthday());

        return personRepository.save(person);
    }
}
