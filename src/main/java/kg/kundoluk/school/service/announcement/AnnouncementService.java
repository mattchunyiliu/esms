package kg.kundoluk.school.service.announcement;

import kg.kundoluk.school.dto.announcement.AnnouncementRequest;
import kg.kundoluk.school.model.announcement.Announcement;

import java.util.List;

public interface AnnouncementService {
    Announcement create(AnnouncementRequest announcementRequest);
    void delete(Long id);
    List<Announcement> findAllByUser(Long userId);
    List<Announcement> findAllBySchoolAndRole(Long schoolId, Long roleId, Long classId);
}
