package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.AnalyticGender;
import kg.kundoluk.school.dto.projection.PersonViewDTO;
import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.enums.Gender;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {


    @EntityGraph(attributePaths = {"user","courses", "courses.course"})
    @Query("SELECT p FROM Person  p LEFT JOIN p.user u LEFT JOIN u.schools sch WHERE sch.id = ?1 and p.archived = ?2 order by p.lastName, p.firstName")
    List<Person> instructorList(Long schoolId, Boolean archived);

    @EntityGraph(attributePaths = {"user"})
    @Query("SELECT p FROM Person  p LEFT JOIN p.user u LEFT JOIN u.schools sch LEFT JOIN u.roles r WHERE sch.id = ?1 and r.id = ?2")
    List<Person> findAllBySchoolAndRole(Long schoolId, Long roleId);

    @EntityGraph(attributePaths = "courses")
    @Query("SELECT p FROM Person p WHERE p.id = ?1")
    Person getPersonWithCourse(Long id);

    @EntityGraph(attributePaths = {"user"})
    Optional<Person> findFirstByUserId(Long userId);

    @EntityGraph(attributePaths = {"user"})
    Optional<Person> findFirstByUserUsername(String username);

    @Query("SELECT p FROM Person p WHERE lower(p.firstName) like %?1% and lower(p.lastName) like %?2% and (?3 = '%-%' or lower(p.middleName) like %?3%) and p.gender = ?4 and p.birthday = ?5 ")
    List<Person> findByTitle(String firstName, String lastName, String middleName, Gender gender, LocalDate dob);

    @EntityGraph(attributePaths = {"user.schools"})
    @Query("SELECT p FROM Person p WHERE lower(p.firstName) like %?1% and lower(p.lastName) like %?2% and (?3 = '%-%' or lower(p.middleName) like %?3%) ")
    List<Person> findByTitle(String firstName, String lastName, String middleName);
}
