package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.AnnouncementClassDTO;
import kg.kundoluk.school.model.announcement.AnnouncementClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnnouncementClassRepository extends JpaRepository<AnnouncementClass, Long> {

    @Query(value = "select ac.id, concat(sc.level,'',sc.label) as classTitle, c.title as courseTitle from announcement_class ac left join school_class sc on ac.class_id = sc.id left join school_course s on ac.course_id = s.id left join course c on s.course_id = c.id where ac.announcement_id=?1", nativeQuery = true)
    List<AnnouncementClassDTO> findAllByAnnouncementId(Long id);
}
