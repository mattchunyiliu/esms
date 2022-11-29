package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.AnalyticGender;
import kg.kundoluk.school.dto.projection.StudentPremiumCount;
import kg.kundoluk.school.dto.projection.StudentViewDTO;
import kg.kundoluk.school.dto.projection.StudentViewMobileDTO;
import kg.kundoluk.school.model.student.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "select s.id, s.first_name as firstName, s.last_name as lastName, s.middle_name as middleName, concat(sc.level,'',sc.label) as classTitle, u.phone_number as phone, s.subscription_type as subscriptionType, s.archived from student s left join users u on u.id = s.user_id left join school_class sc on s.class_id = sc.id where sc.school_id = ?1 and s.archived = ?2 order by sc.level, s.last_name, s.first_name", nativeQuery = true)
    List<StudentViewDTO> findAllBySchool(Long schoolId, Boolean archived);

    @Query(value = "select s.id, s.first_name as firstName, s.last_name as lastName, s.middle_name as middleName, s.gender, u.avatar, u.username, s.archived, s.class_id as classId from student s left join users u on u.id = s.user_id where s.class_id = ?1 and s.archived = false order by s.last_name, s.first_name", nativeQuery = true)
    List<StudentViewMobileDTO> findAllBySchoolClass(Long classId);

    @Query(value = "select s.id, s.first_name as firstName, s.last_name as lastName, s.middle_name as middleName, s.gender, u.avatar, count(sp.id) as parentCount, u.username from student s left join users u on u.id = s.user_id left join student_parent sp on s.id = sp.student_id where s.class_id = ?1 and s.archived = false group by s.id, u.id, s.last_name order by s.last_name", nativeQuery = true)
    List<StudentViewMobileDTO> findAllBySchoolClassWithParent(Long classId);

    @Query(value = "select s.id, s.first_name as firstName, s.last_name as lastName, s.archived, s2.school_name as middleName from student s left join user_school us on s.user_id = us.user_id left join school s2 on us.school_id = s2.id where lower(s.first_name) like %?1% and lower(s.last_name) like %?2% and (?3 = '%-%' or lower(s.middle_name) like %?3%) and s.gender = ?4 and s.birthday = ?5 limit 1", nativeQuery = true)
    StudentViewMobileDTO filterByTitleSQL(String firstName, String lastName, String middleName, Integer gender, LocalDate dob);

    Set<Student> findAllByIdIn(List<Long> idList);

    @EntityGraph(attributePaths = {"user","schoolClass"})
    Student findFirstById(Long id);

    @EntityGraph(attributePaths = {"user","schoolClass","schoolClass.school"})
    Student findFirstByUserId(Long id);

    @Query(value = "select s.id, s.first_name as firstName, s.last_name as lastName, s.middle_name as middleName, s.subscription_type as subscriptionType, s.gender, s.class_id as classId  from student s where s.id=?1 limit 1",nativeQuery = true)
    StudentViewDTO findViewById(Long id);

    @EntityGraph(attributePaths = {"schoolClass"})
    Set<Student> findAllBySchoolClassId(Long classId);

    @EntityGraph(attributePaths = {"schoolClass"})
    @Query("SELECT s FROM Student s LEFT JOIN s.schoolClass sc LEFT JOIN sc.school sch WHERE sch.id = ?1")
    Set<Student> findAllBySchoolId(Long schoolId);

    @Modifying
    @Query(value = "UPDATE student SET subscription_type = ?2 WHERE id IN ?1",nativeQuery = true)
    @Transactional
    void updateSubscription(List<Long> studentList, Integer subscription);

    @Modifying
    @Query(value = "UPDATE student SET archived = ?2 WHERE id = ?1",nativeQuery = true)
    @Transactional
    void updateArchived(Long studentId, Boolean status);

    @EntityGraph(attributePaths = {"schoolClass","schoolClass.person","schoolClass.school","parents.parent"})
    @Query("SELECT s FROM Student s WHERE lower(s.firstName) like %?1% and lower(s.lastName) like %?2% and (?3 = '%-%' or lower(s.middleName) like %?3%)")
    List<Student> searchStudent(String firstName, String lastName, String middleName);
}
