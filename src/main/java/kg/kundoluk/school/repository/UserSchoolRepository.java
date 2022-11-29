package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.UserSchoolDTO;
import kg.kundoluk.school.model.school.School;
import kg.kundoluk.school.model.user.User;
import kg.kundoluk.school.model.user.UserSchool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserSchoolRepository extends JpaRepository<UserSchool, Long> {

    @Query(value = "select us.id, s.id as schoolId, s.school_name as schoolName, s.phone_number as schoolPhone, s.address as schoolAddress from user_school us left join school s on us.school_id = s.id where us.user_id = ?1", nativeQuery = true)
    List<UserSchoolDTO> getUserSchoolByUserId(Long userId);

    Boolean existsAllByUserAndSchool(User user, School school);

    @Modifying
    @Query(value = "DELETE FROM user_school where user_id = ?1 AND school_id = ?2",nativeQuery = true)
    @Transactional
    void deleteByUserSchool(Long userId, Long schoolId);
}
