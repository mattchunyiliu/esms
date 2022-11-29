package kg.kundoluk.school.service.user;

import kg.kundoluk.school.dto.attachment.AttachmentFileResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface UserAvatarFileService {
    AttachmentFileResponseDto uploadMultipartFile(MultipartFile file);
    File downloadByKey(String key);
    void delete(String key);
}
