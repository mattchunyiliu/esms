package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.StudentParentLastDate;
import kg.kundoluk.school.model.user.UserTrackPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface UserTrackPageRepository extends JpaRepository<UserTrackPage, Long> {
    @Modifying
    @Query(value = "INSERT INTO user_track_pages(USER_ID, IP_ADDRESS, PAGE, DEVICE)  VALUES(?1, ?2, ?3, ?4) ", nativeQuery = true)
    @Transactional
    void insertSQL(Long userId, String ip, String page, Integer device);

    @Query(value = "SELECT count(distinct(utp.user_id)) FROM user_track_pages as utp WHERE utp.created_at > ?1 and utp.created_at < ?2 ", nativeQuery = true)
    Integer getAllByDateRangeCount(LocalDateTime start, LocalDateTime end);

    @Query(value = "select distinct on (s.id) concat(s.last_name,' ',s.first_name) as studentTitle, concat(p.last_name,' ',p.first_name) as parentTitle, utp.created_at as lastDate\n" +
            "from student s\n" +
            "    left join student_parent sp on s.id = sp.student_id\n" +
            "    left join person p on sp.parent_id = p.id\n" +
            "    left join user_track_pages utp on p.user_id = utp.user_id\n" +
            "where s.class_id=?1 order by s.id, lastDate desc", nativeQuery = true)
    List<StudentParentLastDate> getClassParentLastDate(Long classId);
}
