package kg.kundoluk.school.repository;

import kg.kundoluk.school.model.announcement.Announcement;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    @EntityGraph(attributePaths = {"user","roles","classes.schoolClass"})
    List<Announcement> findAllByUserId(Long userId);

    @EntityGraph(attributePaths = {"user","roles","classes.schoolClass"})
    @Query("SELECT a from Announcement a LEFT JOIN a.school sch LEFT JOIN a.roles r LEFT JOIN a.classes cl LEFT JOIN cl.schoolClass scl WHERE sch.id=?1 and r.id=?2 and (?3=0l or scl.id=?3) ")
    List<Announcement> findAllBySchool(Long schoolId, Long roleId, Long classId);
}
