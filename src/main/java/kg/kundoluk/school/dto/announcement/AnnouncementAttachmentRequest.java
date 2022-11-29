package kg.kundoluk.school.dto.announcement;

import kg.kundoluk.school.model.announcement.Announcement;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnnouncementAttachmentRequest {
    Announcement announcement;
    String fileUrl;
    String originalTitle;
}
