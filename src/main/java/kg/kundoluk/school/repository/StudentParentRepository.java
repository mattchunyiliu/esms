package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.FirebaseTokenDTO;
import kg.kundoluk.school.model.student.StudentParent;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudentParentRepository extends JpaRepository<StudentParent, Long> {
    @EntityGraph(attributePaths = {"parent","parent.user","student"})
    List<StudentParent> findAllByStudentId(Long id);

    @EntityGraph(attributePaths = {"parent","parent.user","student"})
    List<StudentParent> findAllByStudentSchoolClassId(Long classId);

    @EntityGraph(attributePaths = {"student","student.user","student.schoolClass","student.schoolClass.school"})
    List<StudentParent> findAllByParentId(Long id);

    @EntityGraph(attributePaths = {"parent","parent.user","student"})
    List<StudentParent> findAllByParentUserUsername(String username);

    @Modifying
    @Query(value = "UPDATE student_parent SET parent_id = ?2, parental_type = ?3 WHERE id = ?1",nativeQuery = true)
    @Transactional
    void updateParent(Long studentParentId, Long personId, Integer parentalTYpe);

    @Query(value = "select l.code as languageCode, m2muft.firebase_token as token, u.id as userId from student_parent sp " +
            "left join person p on p.id = sp.parent_id " +
            "left join users u on p.user_id = u.id " +
            "left join language l on l.id = u.language_id " +
            "left join m2m_user_firebase_token m2muft on u.id = m2muft.user_id " +
            "where sp.student_id = ?1",nativeQuery = true)
    List<FirebaseTokenDTO> findParentTokensByStudent(Long studentId);

    @Query(value = "select l.code as languageCode, m2muft.firebase_token as token, u.id as userId " +
            "from student_parent sp " +
            "left join student s on sp.student_id = s.id " +
            "left join person p on p.id = sp.parent_id " +
            "left join users u on p.user_id = u.id " +
            "left join language l on l.id = u.language_id " +
            "left join m2m_user_firebase_token m2muft on u.id = m2muft.user_id " +
            "where s.class_id = ?1",nativeQuery = true)
    List<FirebaseTokenDTO> findParentTokensByClass(Long classId);
}
