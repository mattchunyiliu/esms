package kg.kundoluk.school.service.async;

import kg.kundoluk.school.model.instructor.Assignment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AssignmentAttachmentAsyncUploadService {
    void uploadAttachments(MultipartFile[] multipartFiles, Assignment assignment) throws IOException;
}
