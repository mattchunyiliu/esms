package kg.kundoluk.school.controller.web.announcement;

import kg.kundoluk.school.dto.projection.AnnouncementClassDTO;
import kg.kundoluk.school.service.announcement.AnnouncementClassService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/web/v1/announcement/{announcementId:[\\d]+}/class")
public class AnnouncementClassController {

    private final AnnouncementClassService announcementClassService;

    public AnnouncementClassController(AnnouncementClassService announcementClassService) {
        this.announcementClassService = announcementClassService;
    }

    @GetMapping
    public List<AnnouncementClassDTO> getAnnouncementClasses(
            @PathVariable(name = "announcementId") Long announcementId
    ) {
        return announcementClassService.findAllByAnnouncement(announcementId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") Long id,
            @PathVariable String announcementId
    ) {
        announcementClassService.delete(id);
        return ResponseEntity.ok().build();
    }
}
