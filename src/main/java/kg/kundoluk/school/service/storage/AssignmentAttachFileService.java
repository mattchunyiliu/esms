package kg.kundoluk.school.service.storage;

import kg.kundoluk.school.dto.attachment.AttachmentFileResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface AssignmentAttachFileService {
    AttachmentFileResponseDto uploadMultipartFile(MultipartFile file) throws IOException;
    File downloadByKey(String key);
    void delete(String key);
}
