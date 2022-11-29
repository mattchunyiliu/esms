package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.AnalyticClassGender;
import kg.kundoluk.school.dto.projection.AnalyticGender;
import kg.kundoluk.school.dto.projection.StudentPremiumCount;
import kg.kundoluk.school.model.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentAnalyticRepository extends JpaRepository<Student, Long> {

    @Query(value = "select sch.school_name as schoolTitle, count(s.id) as studentCount, count(CASE WHEN s.subscription_type=1 THEN 1 END) as premiumCount\n" +
            "from student s\n" +
            "    left join user_school us on s.user_id = us.user_id\n" +
            "    left join school sch on us.school_id = sch.id where s.archived=false or s.archived is null group by sch.id",nativeQuery = true)
    List<StudentPremiumCount> getPremiumCount();

    @Query(value = "select count(case when s.gender = 1 then 1 end) as maleCount,\n" +
            "       count(case when s.gender = 2 then 1 end) as femaleCount, sch.id as schoolId,\n" +
            "       sch.school_name as schoolName,\n" +
            "       sch.school_type as schoolType,\n" +
            "       sch.rayon_id as rayonId,\n" +
            "       sch.region_id as regionId,\n" +
            "       sch.town_id as townId\n" +
            "from student s\n" +
            "    left join user_school us on s.user_id = us.user_id\n" +
            "    left join school sch on sch.id = us.school_id\n" +
            "where s.archived = false and (?1 = 0 or us.school_id = ?1) and (?2 = 0 or sch.rayon_id = ?2) and (?3 = 0 or sch.town_id = ?3) group by sch.id", nativeQuery = true)
    List<AnalyticGender> getStudentGenderAnalytic(Long schoolId, Long rayonId, Long townId);

    @Query(value = "select count(case when s.gender = 1 then 1 end) as maleCount,\n" +
            "       count(case when s.gender = 2 then 1 end) as femaleCount,\n" +
            "       scl.id as classId,\n" +
            "       concat(scl.level,'',scl.label) as classTitle\n" +
            "from student s left join school_class scl on s.class_id = scl.id\n" +
            "where scl.school_id = ?1 and s.archived = false and (?2 = 0 or scl.id = ?2) group by scl.id", nativeQuery = true)
    List<AnalyticClassGender> getStudentClassGenderAnalytic(Long schoolId, Long classId);
}
