package kg.kundoluk.school.service.announcement;

import kg.kundoluk.school.dto.announcement.AnnouncementClassRequest;
import kg.kundoluk.school.dto.projection.AnnouncementClassDTO;
import kg.kundoluk.school.model.announcement.AnnouncementClass;

import java.util.List;

public interface AnnouncementClassService {
    AnnouncementClass create(AnnouncementClassRequest announcementClassRequest);
    void delete(Long id);
    List<AnnouncementClassDTO> findAllByAnnouncement(Long announcementId);
}
