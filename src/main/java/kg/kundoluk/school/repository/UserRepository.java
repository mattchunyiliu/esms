package kg.kundoluk.school.repository;

import kg.kundoluk.school.constants.CacheService;
import kg.kundoluk.school.dto.projection.SchoolAdminViewDTO;
import kg.kundoluk.school.dto.projection.UserRayonDTO;
import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.enums.Gender;
import kg.kundoluk.school.model.user.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"roles"})
    User findByUsername(String username);

    @Cacheable(value = CacheService.USER, key = "#username", unless="#username != null")
    @EntityGraph(attributePaths = {"roles"})
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findByUsernameCached(String username);

    @EntityGraph(attributePaths = {"roles","schools"})
    User getByUsername(String username);

    @EntityGraph(attributePaths = {"roles","schools"})
    User findFirstByPhone(String phone);

    Boolean existsByUsername(String username);

    Boolean existsByPhone(String phone);

    @Modifying
    @Query(value = "UPDATE users SET language_id = ?2 WHERE id = ?1",nativeQuery = true)
    @Transactional
    void updateLanguage(Long userId, Long languageId);

    @Query(value = "select u.id, u.phone_number as phone, u.first_name as firstName, u.last_name as lastName, u.username, sch.school_name as schoolName, rlr.title as rayonTitle, r.title as regionTitle " +
            "from users u " +
            "left join user_school us on us.user_id = u.id " +
            "left join user_roles ur on ur.user_id = u.id " +
            "left join school sch on sch.id = us.school_id " +
            "left join ref_location_rayon rlr on sch.rayon_id = rlr.id " +
            "left join ref_location_region r on rlr.region_id = r.id " +
            "where ur.role_id = 2", nativeQuery = true)
    List<SchoolAdminViewDTO> listAdmin();

    @Query(value = "select u.id, u.phone_number as phone, u.first_name as firstName, u.last_name as lastName, u.username,  rlr.title as rayonTitle, r.title as regionTitle " +
            "from user_rayon ur " +
            "left join users u on u.id = ur.user_id " +
            "left join ref_location_rayon rlr on ur.rayon_id = rlr.id " +
            "left join ref_location_region r on rlr.region_id = r.id", nativeQuery = true)
    List<SchoolAdminViewDTO> listRayonAdmin();

    @EntityGraph(attributePaths = {"roles"})
    @Query("SELECT u FROM User u INNER JOIN u.schools sch INNER JOIN u.roles r WHERE sch.id = ?1 and r.id = ?2")
    List<User> findAllBySchoolsAndRoles(Long schoolId, Long roleId);

    @Query(value = "select rlr.id as rayonId, rlr.title as rayonTitle from user_rayon ur left join ref_location_rayon rlr on rlr.id = ur.rayon_id where ur.user_id=?1", nativeQuery = true)
    List<UserRayonDTO> listUserRayon(Long userId);

    @EntityGraph(attributePaths = {"roles","schools"})
    @Query("SELECT u FROM User u WHERE (?1 ='%-%' or lower(u.firstName) like %?1%) and (lower(u.lastName) like %?2%) and (?3 = '%-%' or lower(u.middleName) like %?3%)")
    List<User> findByTitle(String firstName, String lastName, String middleName);
}
