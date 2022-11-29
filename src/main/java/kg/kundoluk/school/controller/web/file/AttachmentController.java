package kg.kundoluk.school.controller.web.file;

import kg.kundoluk.school.dto.attachment.AttachmentFileResponseDto;
import kg.kundoluk.school.endpoint.AttachmentEndpoint;
import kg.kundoluk.school.service.storage.AttachmentFileService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/web/v1/attachment")
public class AttachmentController {
    private final AttachmentEndpoint attachmentEndpoint;

    public AttachmentController(AttachmentEndpoint attachmentEndpoint) {
        this.attachmentEndpoint = attachmentEndpoint;
    }

    @PostMapping("/upload")
    public AttachmentFileResponseDto uploadAttachment(
            @RequestPart(name = "file") MultipartFile file
    ) throws IOException {
        return attachmentEndpoint.uploadMultipartFile(file);
    }

    @PostMapping(value = "/match",produces = "text/csv")
    public ResponseEntity<Resource> match(
            @RequestPart(name = "file") MultipartFile file,
            @RequestParam("schoolId") Long schoolId
    ) throws IOException {

        Resource resource = attachmentEndpoint.match(file, schoolId);
        String contentType = "text/csv";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(new FileSystemResource(resource.getFile()));

    }
}
