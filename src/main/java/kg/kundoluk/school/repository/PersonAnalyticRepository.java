package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.AnalyticGender;
import kg.kundoluk.school.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonAnalyticRepository extends JpaRepository<Person, Long> {

    @Query(value = "select count(case when s.gender = 1 then 1 end) as maleCount,\n" +
            "       count(case when s.gender = 2 then 1 end) as femaleCount, sch.id as schoolId,\n" +
            "       sch.school_name as schoolName,\n" +
            "       sch.school_type as schoolType, sch.organization_type as schoolOrganizationType, sch.language_type as schoolLanguageType, \n" +
            "       sch.rayon_id as rayonId,\n" +
            "       sch.region_id as regionId,\n" +
            "       sch.town_id as townId\n" +
            "from person s\n" +
            "    left join user_school us on s.user_id = us.user_id\n" +
            "    left join school sch on sch.id = us.school_id\n" +
            "where s.archived = false and (?1 = 0 or us.school_id = ?1) and (?2 = 0 or sch.rayon_id = ?2) and (?3 = 0 or sch.town_id = ?3) group by sch.id", nativeQuery = true)
    List<AnalyticGender> getInstructorGenderAnalytic(Long schoolId, Long rayonId, Long townId);

    @Query(value = "select count(case when p.gender = 1 then 1 end) as maleCount,\n" +
            "       count(case when p.gender = 2 then 1 end) as femaleCount, sch.id as schoolId,\n" +
            "       sch.school_name as schoolName,\n" +
            "       sch.school_type as schoolType,\n" +
            "       sch.rayon_id as rayonId,\n" +
            "       sch.region_id as regionId\n" +
            "from person p\n" +
            "         left join student_parent sp on sp.parent_id = p.id\n" +
            "         left join student s on s.id = sp.student_id\n" +
            "         left join user_school us on s.user_id = us.user_id\n" +
            "         left join school sch on sch.id = us.school_id\n" +
            "where p.archived = false and (?1 = 0 or us.school_id = ?1) group by sch.id", nativeQuery = true)
    List<AnalyticGender> getParentGenderAnalytic(Long schoolId);
}
