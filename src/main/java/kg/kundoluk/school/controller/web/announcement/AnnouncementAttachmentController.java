package kg.kundoluk.school.controller.web.announcement;

import kg.kundoluk.school.dto.projection.AnnouncementAttachmentDTO;
import kg.kundoluk.school.service.announcement.AnnouncementAttachmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/web/v1/announcement/{announcementId:[\\d]+}/attachment")
public class AnnouncementAttachmentController {
    private final AnnouncementAttachmentService announcementAttachmentService;

    public AnnouncementAttachmentController(AnnouncementAttachmentService announcementAttachmentService) {
        this.announcementAttachmentService = announcementAttachmentService;
    }

    @GetMapping
    public List<AnnouncementAttachmentDTO> getAnnouncementAttachments(
            @PathVariable(name = "announcementId") Long announcementId
    ) {
        return announcementAttachmentService.findAllByAnnouncement(announcementId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") Long id,
            @PathVariable String announcementId
    ) {
        announcementAttachmentService.delete(id);
        return ResponseEntity.ok().build();
    }
}
