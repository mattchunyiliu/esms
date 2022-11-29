package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.AnnouncementAttachmentDTO;
import kg.kundoluk.school.model.announcement.AnnouncementAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnnouncementAttachmentRepository extends JpaRepository<AnnouncementAttachment, Long> {

    @Query(value = "select id, file_url as fileUrl, original_title as originalTitle from announcement_attachments where announcement_id = ?1", nativeQuery = true)
    List<AnnouncementAttachmentDTO> findAllByAnnouncementId(Long id);
}
