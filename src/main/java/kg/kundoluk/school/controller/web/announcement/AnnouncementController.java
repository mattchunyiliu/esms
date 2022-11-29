package kg.kundoluk.school.controller.web.announcement;

import kg.kundoluk.school.components.hateoas.assembler.AnnouncementResourceAssembler;
import kg.kundoluk.school.components.hateoas.resource.AnnouncementResource;
import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.announcement.AnnouncementCreateRequest;
import kg.kundoluk.school.endpoint.AnnouncementEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/web/v1/announcement")
public class AnnouncementController {
    private final AnnouncementEndpoint announcementEndpoint;
    private final AnnouncementResourceAssembler announcementResourceAssembler;

    public AnnouncementController(AnnouncementEndpoint announcementEndpoint, AnnouncementResourceAssembler announcementResourceAssembler) {
        this.announcementEndpoint = announcementEndpoint;
        this.announcementResourceAssembler = announcementResourceAssembler;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<?> create(
            @ModelAttribute AnnouncementCreateRequest announcementCreateRequest,
            @RequestParam(name = "files", required = false) MultipartFile[] files
    ) throws IOException {
        announcementEndpoint.create(announcementCreateRequest, files);
        return new ResponseEntity<>(new ApiResponse(true, "CREATED"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") Long id
    ) {
        announcementEndpoint.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public List<AnnouncementResource> getUserAnnouncements(
            @PathVariable("userId") Long userId
    ) {
        return  announcementEndpoint.findAllByUser(userId).stream().map(announcementResourceAssembler::toModel).collect(Collectors.toList());
    }

    @GetMapping("/school/{schoolId}/role/{roleId}")
    public List<AnnouncementResource> getSchoolAnnouncements(
            @PathVariable("schoolId") Long schoolId,
            @PathVariable("roleId") Long roleId,
            @RequestParam(value = "classId", required = false) Long classId
    ) {
        return  announcementEndpoint.findAllBySchoolAndRole(schoolId, roleId, classId).stream().map(announcementResourceAssembler::toModel).collect(Collectors.toList());
    }
}
