package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.constants.CacheService;
import kg.kundoluk.school.dto.person.PersonCourseBulkRequest;
import kg.kundoluk.school.dto.person.PersonCourseCreateRequest;
import kg.kundoluk.school.dto.projection.SchoolCourseDTO;
import kg.kundoluk.school.endpoint.PersonCourseEndpoint;
import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.school.SchoolCourse;
import kg.kundoluk.school.repository.PersonRepository;
import kg.kundoluk.school.repository.SchoolCourseRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
@Service
public class PersonCourseEndpointImpl implements PersonCourseEndpoint {
    private final PersonRepository personRepository;
    private final SchoolCourseRepository courseRepository;

    public PersonCourseEndpointImpl(PersonRepository personRepository, SchoolCourseRepository courseRepository) {
        this.personRepository = personRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void addCourse(PersonCourseCreateRequest personCourseCreateRequest) {
        Person person = personRepository.getPersonWithCourse(personCourseCreateRequest.getPersonId());
        SchoolCourse course = courseRepository.getOne(personCourseCreateRequest.getCourseId());
        person.addCourse(course);
        personRepository.save(person);
    }

    @Override
    public void removeCourse(PersonCourseCreateRequest personCourseCreateRequest) {
        Person person = personRepository.getPersonWithCourse(personCourseCreateRequest.getPersonId());
        SchoolCourse course = courseRepository.getOne(personCourseCreateRequest.getCourseId());
        person.removeCourse(course);
        personRepository.save(person);
    }

    @Override
    public void addBulkCourse(PersonCourseBulkRequest personCourseBulkRequest) {
        Person person = personRepository.getPersonWithCourse(personCourseBulkRequest.getPersonId());
        person.setCourses(courseRepository.getAllByIdIn(personCourseBulkRequest.getCourseList()));
        personRepository.save(person);
    }

    @Override
    public List<SchoolCourseDTO> getAllPersonCourse(Long personId, Long schoolId) {
        if (schoolId == null)
            schoolId = 0L;
        return courseRepository.findAllByPersonId(personId, schoolId);
    }

}
