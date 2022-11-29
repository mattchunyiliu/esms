package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.announcement.AnnouncementCreateRequest;
import kg.kundoluk.school.model.announcement.Announcement;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AnnouncementEndpoint {
    Announcement create(AnnouncementCreateRequest announcementCreateRequest, MultipartFile[] files) throws IOException;
    List<Announcement> findAllByUser(Long userId);
    List<Announcement> findAllBySchoolAndRole(Long schoolId, Long roleId, Long classId);
    void delete(Long id);
}
