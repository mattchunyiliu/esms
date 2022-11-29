package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.attachment.AttachmentFileResponseDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AttachmentEndpoint {
    AttachmentFileResponseDto uploadMultipartFile(MultipartFile file) throws IOException;
    Resource match(MultipartFile file, Long schoolId) throws IOException;
}
