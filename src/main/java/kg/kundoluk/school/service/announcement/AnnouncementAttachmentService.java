package kg.kundoluk.school.service.announcement;

import kg.kundoluk.school.dto.announcement.AnnouncementAttachmentRequest;
import kg.kundoluk.school.dto.projection.AnnouncementAttachmentDTO;
import kg.kundoluk.school.model.announcement.AnnouncementAttachment;


import java.util.List;

public interface AnnouncementAttachmentService {
    AnnouncementAttachment create(AnnouncementAttachmentRequest announcementAttachmentRequest);
    void delete(Long id);
    void deleteList(List<Long> ids);
    List<AnnouncementAttachmentDTO> findAllByAnnouncement(Long announcementId);
}
