package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.SchoolCourseDTO;
import kg.kundoluk.school.model.school.SchoolCourse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface SchoolCourseRepository extends JpaRepository<SchoolCourse, Long> {

    @Query(value = "select sc.id, c.title, c.title_kg as titleKG, c.title_ru as titleRU, c.title_tr as titleTR, c.id as courseId from school_course sc left join course c on sc.course_id = c.id where sc.school_id = ?1", nativeQuery = true)
    List<SchoolCourseDTO> findAllBySchoolId(Long schoolId);

    @Query(value = "select sc.id, c.title, c.title_kg as titleKG, c.title_ru as titleRU, c.title_tr as titleTR, c.id as courseId from m2m_instructor_course m2mic left join school_course sc on m2mic.course_id = sc.id left join course c on sc.course_id = c.id  where m2mic.instructor_id = ?1 and (?2 = 0 or sc.school_id = ?2)", nativeQuery = true)
    List<SchoolCourseDTO> findAllByPersonId(Long personId, Long schoolId);

    Boolean existsBySchoolIdAndCourseId(Long schoolId, Long courseId);

    Set<SchoolCourse> getAllByIdIn(List<Long> ids);
}
