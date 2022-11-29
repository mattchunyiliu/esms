package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.AnalyticSchoolCount;
import kg.kundoluk.school.dto.projection.AssignmentInstructorClass;
import kg.kundoluk.school.dto.projection.GradeCountAnalytic;
import kg.kundoluk.school.model.instructor.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AssignmentAnalyticRepository extends JpaRepository<Assignment, Long> {

    @Query(value = "select concat(p.last_name,' ',p.first_name) as instructorTitle, count(a.id) as totalCount\n" +
            "from person p left join calendar_plan cp on cp.person_id = p.id\n" +
            "              left join calendar_topic ct on ct.calendar_plan_id = cp.id\n" +
            "              left join assignment a on ct.id = a.topic_id\n" +
            "              left join school_class sc on cp.class_id = sc.id\n" +
            "where sc.school_id=?1 and (a.created_at is null or a.created_at between ?2 and ?3) and p.archived=false group by p.id, p.last_name order by p.last_name", nativeQuery = true)
    List<GradeCountAnalytic> getInstructorAssignmentCount(Long schoolId, LocalDate start, LocalDate end);

    @Query(value = "select sch.id as schoolId, sch.school_name as schoolName, count(a.id) as totalCount\n" +
            "from school sch\n" +
            "         left join school_class scl on sch.id = scl.school_id\n" +
            "         left join calendar_plan cp on scl.id = cp.class_id\n" +
            "         left join calendar_topic ct on cp.id = ct.calendar_plan_id\n" +
            "         left join assignment a on ct.id = a.topic_id\n" +
            "where sch.rayon_id=?1 and (?2 = 0 or sch.town_id = ?2) and (a.created_at is null or a.created_at between ?3 and ?4) group by sch.id", nativeQuery = true)
    List<AnalyticSchoolCount> getAssignmentCountBySchool(Long rayonId, Long townId, LocalDate start, LocalDate end);

    @Query(value = "select concat(scl.level,'',scl.label) as classTitle,\n" +
            "       ct.title as topicTitle,\n" +
            "       a.deadline_at as deadlineAt,\n" +
            "       c.title as courseTitle,\n" +
            "       c.title_ru as courseTitleRU,\n" +
            "       c.title_kg as courseTitleKG\n" +
            "from assignment a\n" +
            "    left join calendar_topic ct on a.topic_id = ct.id\n" +
            "    left join calendar_plan cp on ct.calendar_plan_id = cp.id\n" +
            "    left join school_class scl on cp.class_id = scl.id\n" +
            "    left join school_course s on cp.course_id = s.id\n" +
            "    left join course c on s.course_id = c.id\n" +
            "where cp.person_id = ?1 and cp.class_id=?2", nativeQuery = true)
    List<AssignmentInstructorClass> getAssignmentByInstructorClass(Long instructorId, Long classId);
}
